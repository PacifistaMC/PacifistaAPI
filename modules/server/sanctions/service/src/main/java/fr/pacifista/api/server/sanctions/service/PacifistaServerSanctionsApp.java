package fr.pacifista.api.server.sanctions.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.funixproductions")
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaServerSanctionsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaServerSanctionsApp.class, args);
    }
}
