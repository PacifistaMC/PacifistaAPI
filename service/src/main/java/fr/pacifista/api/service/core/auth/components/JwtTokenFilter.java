package fr.pacifista.api.service.core.auth.components;

import com.funixproductions.api.client.user.clients.UserAuthClient;
import com.funixproductions.api.client.user.dtos.UserDTO;
import com.funixproductions.api.client.user.enums.UserRole;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.net.HttpHeaders;
import feign.FeignException;
import fr.pacifista.api.service.core.auth.entities.Session;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserAuthClient authClient;

    private final Cache<String, UserDTO> sessionsCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES).build();

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final UserDTO userDTO = fetchActualUser(header);
            final Session session = new Session(userDTO, header, request);
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(session, null, getAuthoritiesFromUser(userDTO));

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (FeignException e) {
            final String code = Integer.toString(e.status());

            if (code.startsWith("5") || code.equals("-1")) {
                throw new ApiException("Le serveur d'authentification est indisponible ou rencontre une erreur.", e);
            } else {
                throw new ApiBadRequestException("Vous n'avez pas le bon token d'authentification. Veuillez vous reconnecter.", e);
            }
        }
    }

    private UserDTO fetchActualUser(final String headerAuth) throws FeignException {
        UserDTO userDTO = this.sessionsCache.getIfPresent(headerAuth);

        if (userDTO == null) {
            userDTO = this.authClient.current(headerAuth);
            this.sessionsCache.put(headerAuth, userDTO);
        }
        return userDTO;
    }

    private List<SimpleGrantedAuthority> getAuthoritiesFromUser(final UserDTO userDTO) {
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));

        final UserRole role = userDTO.getRole();
        if (role == UserRole.ADMIN || role == UserRole.PACIFISTA_ADMIN) {
            authorities.addAll(List.of(
                    new SimpleGrantedAuthority(UserRole.PACIFISTA_ADMIN.getRole()),
                    new SimpleGrantedAuthority(UserRole.PACIFISTA_MODERATOR.getRole())
            ));
        } else if (role == UserRole.PACIFISTA_MODERATOR) {
            authorities.add(new SimpleGrantedAuthority(UserRole.PACIFISTA_MODERATOR.getRole()));
        }
        return authorities;
    }
}
