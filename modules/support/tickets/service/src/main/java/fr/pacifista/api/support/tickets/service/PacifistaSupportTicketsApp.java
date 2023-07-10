package fr.pacifista.api.support.tickets.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
        "com.funixproductions"
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaSupportTicketsApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaSupportTicketsApp.class, args);
    }
}
