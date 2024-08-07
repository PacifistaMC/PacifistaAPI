package fr.pacifista.api.web.user.service.security;

import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.api.user.client.security.ApiWebSecurity;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
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
                .requestMatchers("/kubeinternal/**").permitAll()

                .requestMatchers("/" + PacifistaWebUserLinkClientImpl.PATH + "/public/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/web/user/legal-document").permitAll()
                .requestMatchers("/web/user/legal-document").hasAuthority(UserRole.ADMIN.getRole())
                .requestMatchers("/web/user/legal-document-user").hasAuthority(UserRole.ADMIN.getRole())
                .requestMatchers("/web/user/legal-document-user/self").authenticated()

                .anyRequest().hasAuthority(UserRole.PACIFISTA_ADMIN.getRole());
    }
}
