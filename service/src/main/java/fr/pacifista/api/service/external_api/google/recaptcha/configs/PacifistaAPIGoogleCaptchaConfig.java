package fr.pacifista.api.service.external_api.google.recaptcha.configs;

import fr.funixgaming.api.core.external.google.captcha.config.GoogleCaptchaConfig;
import fr.funixgaming.api.core.external.google.captcha.services.GoogleCaptchaService;
import fr.funixgaming.api.core.utils.network.IPUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("google.recaptcha.key")
public class PacifistaAPIGoogleCaptchaConfig implements GoogleCaptchaConfig {

    private String site;
    private String secret;
    private Float threshold;
    private boolean disabled;

    @Bean
    GoogleCaptchaService googleCaptchaService(IPUtils ipUtils) {
        return new GoogleCaptchaService(this, ipUtils);
    }

}
