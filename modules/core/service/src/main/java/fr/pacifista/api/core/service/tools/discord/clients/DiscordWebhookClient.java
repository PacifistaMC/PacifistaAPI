package fr.pacifista.api.core.service.tools.discord.clients;

import fr.pacifista.api.core.service.tools.discord.dtos.DiscordSendMessageWebHookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "discord-webhook-client",
        url = "https://discord.com",
        path = "/api/webhooks/"
)
public interface DiscordWebhookClient {

    @PostMapping(value = "/{webhookId}/{webhookToken}")
    void sendMessage(
            @PathVariable String webhookId,
            @PathVariable String webhookToken,
            @RequestBody DiscordSendMessageWebHookDTO body
    );

}
