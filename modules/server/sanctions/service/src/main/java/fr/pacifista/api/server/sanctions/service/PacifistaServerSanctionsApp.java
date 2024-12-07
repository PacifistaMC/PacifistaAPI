package fr.pacifista.api.server.sanctions.service;

import com.funixproductions.api.encryption.client.clients.EncryptionClient;
import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.core.service.tools.discord.clients.DiscordWebhookClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {UserAuthClient.class, DiscordWebhookClient.class, EncryptionClient.class})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaServerSanctionsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerSanctionsApp.class, args);
    }
}
