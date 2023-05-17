package fr.pacifista.api.service.core.auth.components;

import com.funixproductions.api.client.user.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class PacifistaWebSecurity {

    private final JwtTokenFilter jwtTokenFilter;

    public PacifistaWebSecurity(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = http.cors(httpSecurityCorsConfigurer ->httpSecurityCorsConfigurer.configurationSource(httpServletRequest -> {
                    final CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.addAllowedOriginPattern("*");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    return corsConfiguration;
                })).csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(Customizer.withDefaults())

                .authorizeHttpRequests(exchanges -> exchanges
                        .requestMatchers("/sanctions/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())

                        .requestMatchers("/warps/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())

                        .requestMatchers("/box/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers("/permissions/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers("/roles/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers("/playersync/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers("/guilds/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers(HttpMethod.GET, "/web/**").permitAll()
                        .requestMatchers("/web/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                        .requestMatchers("/support/ticket/message/web**").authenticated()
                        .requestMatchers("/support/ticket/message/web/ws").permitAll()
                        .requestMatchers("/support/ticket/web**").authenticated()
                        .requestMatchers("/support/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())

                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
