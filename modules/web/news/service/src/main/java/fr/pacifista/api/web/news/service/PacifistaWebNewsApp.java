package fr.pacifista.api.web.news.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
        "com.funixproductions",
        "fr.pacifista.api.web.user.client.clients",
        "fr.pacifista.api.core.service.tools.discord"
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaWebNewsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaWebNewsApp.class, args);
    }
}
