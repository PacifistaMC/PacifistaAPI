package fr.pacifista.api.web.user.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {
        UserAuthClient.class,
        PacifistaPlayerDataInternalClient.class
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaWebUserApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaWebUserApp.class, args);
    }
}