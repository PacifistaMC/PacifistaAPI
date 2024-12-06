package fr.pacifista.api.support.tickets.service;

import com.funixproductions.api.google.recaptcha.client.clients.GoogleRecaptchaInternalClient;
import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.core.service.tools.discord.clients.DiscordWebhookClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {UserAuthClient.class, DiscordWebhookClient.class, GoogleRecaptchaInternalClient.class})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaSupportTicketsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaSupportTicketsApp.class, args);
    }
}
