package fr.pacifista.api.server.sanctions.service.security;

import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.api.user.client.security.ApiWebSecurity;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurity extends ApiWebSecurity {

    @NotNull
    @Override
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getUrlsMatchers() {
        return ex -> ex
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/sanctions").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())
                .requestMatchers(HttpMethod.GET, "/sanctions/**").hasAuthority(UserRole.PACIFISTA_MODERATOR.getRole())
                .anyRequest().hasAuthority(UserRole.PACIFISTA_ADMIN.getRole());
    }
}
