package fr.pacifista.api.server.guilds.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = UserAuthClient.class)
@SpringBootApplication(scanBasePackages = {
        "fr.pacifista",
        "com.funixproductions"
})
public class PacifistaServerGuildsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerGuildsApp.class, args);
    }
}