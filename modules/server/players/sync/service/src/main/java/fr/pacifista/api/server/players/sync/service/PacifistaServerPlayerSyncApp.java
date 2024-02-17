package fr.pacifista.api.server.players.sync.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = UserAuthClient.class)
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaServerPlayerSyncApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerPlayerSyncApp.class, args);
    }
}
