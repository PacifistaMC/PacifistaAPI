package fr.pacifista.api.server.warps.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = UserAuthClient.class)
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaServerWarpApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerWarpApp.class, args);
    }
}
