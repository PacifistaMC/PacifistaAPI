package fr.pacifista.api.web.shop.service.security;

import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.api.user.client.security.ApiWebSecurity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurity extends ApiWebSecurity {
    @NotNull
    @Override
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getUrlsMatchers() {
        return ex -> ex
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/web/**").permitAll()
                .requestMatchers("/web/**").hasAuthority(UserRole.PACIFISTA_ADMIN.getRole());
    }
}
