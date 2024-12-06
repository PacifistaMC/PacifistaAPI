package fr.pacifista.api.core.service.tools.discord.services;

import com.google.common.base.Strings;
import fr.pacifista.api.core.service.tools.discord.clients.DiscordWebhookClient;
import fr.pacifista.api.core.service.tools.discord.configs.DiscordWebhookConfig;
import fr.pacifista.api.core.service.tools.discord.dtos.DiscordSendMessageWebHookDTO;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "DiscordMessagesService")
public class DiscordMessagesService {

    private final DiscordWebhookConfig webhookConfig;
    private final DiscordWebhookClient webhookClient;

    public final void sendAlertMessage(final DiscordSendMessageWebHookDTO message) {
        if (Strings.isNullOrEmpty(webhookConfig.getWebhookAlertId()) || Strings.isNullOrEmpty(webhookConfig.getWebhookAlertToken())) {
            log.warn("Webhook id or token is empty, message will not be sent.");
            return;
        }

        try {
            this.webhookClient.sendMessage(
                    this.webhookConfig.getWebhookAlertId(),
                    this.webhookConfig.getWebhookAlertToken(),
                    message
            );
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("Error while sending message to discord", e);
        }
    }

}
