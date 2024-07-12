package fr.pacifista.api.web.vote.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
@EnableScheduling
public class PacifistaWebVoteApp {

    public static void main(String[] args) {
        SpringApplication.run(PacifistaWebVoteApp.class, args);
    }

}
