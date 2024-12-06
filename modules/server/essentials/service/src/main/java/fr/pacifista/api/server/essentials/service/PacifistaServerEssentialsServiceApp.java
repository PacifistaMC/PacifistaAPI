package fr.pacifista.api.server.essentials.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.core.service.tools.discord.clients.DiscordWebhookClient;
import fr.pacifista.api.server.essentials.service.status.clients.PacifistaInternalStatusClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(clients = {
        UserAuthClient.class,
        PacifistaInternalStatusClient.class,
        DiscordWebhookClient.class
})
@SpringBootApplication(scanBasePackages = {
        "fr.pacifista",
        "com.funixproductions"
})
@EnableScheduling
public class PacifistaServerEssentialsServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerEssentialsServiceApp.class, args);
    }
}