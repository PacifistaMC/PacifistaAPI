package fr.pacifista.api.service.auth.components;

import com.google.common.net.HttpHeaders;
import feign.FeignException;
import fr.funixgaming.api.client.user.clients.UserAuthClient;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.client.user.enums.UserRole;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserAuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final UserDTO userDTO = authClient.current(header);
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDTO, null, getAuthoritiesFromUser(userDTO));

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
