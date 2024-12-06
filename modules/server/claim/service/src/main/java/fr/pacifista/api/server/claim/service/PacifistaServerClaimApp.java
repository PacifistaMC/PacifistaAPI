package fr.pacifista.api.server.claim.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.core.service.tools.discord.clients.DiscordWebhookClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {UserAuthClient.class, DiscordWebhookClient.class})
@SpringBootApplication(scanBasePackages = {
        "fr.pacifista",
        "com.funixproductions"
})
public class PacifistaServerClaimApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerClaimApp.class, args);
    }
}
