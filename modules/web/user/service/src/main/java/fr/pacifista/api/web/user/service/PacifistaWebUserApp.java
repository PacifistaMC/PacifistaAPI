package fr.pacifista.api.web.user.service;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.web.user.client.components.PacifistaWebLegalComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableFeignClients(clients = {
        UserAuthClient.class,
        PacifistaPlayerDataInternalClient.class
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
@ComponentScan(
        basePackages = {
                "com.funixproductions",
                "fr.pacifista"
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = PacifistaWebLegalComponent.class
        )
)
public class PacifistaWebUserApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaWebUserApp.class, args);
    }
}
