package fr.pacifista.api.core.service.tools.discord.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("discord.webhook")
public class DiscordWebhookConfig {

    /**
     * The webhook staff alerts id.
     */
    private String webhookAlertId = "";

    /**
     * The webhook staff alerts token.
     */
    private String webhookAlertToken = "";

}
