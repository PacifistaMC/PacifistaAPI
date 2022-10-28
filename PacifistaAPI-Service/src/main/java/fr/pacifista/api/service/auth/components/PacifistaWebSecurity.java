package fr.pacifista.api.service.auth.components;

import fr.funixgaming.api.client.user.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

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
        http = http.cors().and().csrf().disable();

        //Set unauthorized requests exception handler
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        http.authorizeRequests()
                .antMatchers("/sanctions/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())
                .antMatchers("/warps/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())

                .antMatchers("/box/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())
                .antMatchers("/permissions/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())
                .antMatchers("/roles/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())
                .antMatchers("/playersync/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())
                .antMatchers("/guilds/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole())

                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
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
