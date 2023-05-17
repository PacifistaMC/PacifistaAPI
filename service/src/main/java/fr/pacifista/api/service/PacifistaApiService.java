package fr.pacifista.api.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients(basePackages = {"fr.pacifista.api", "com.funixproductions.api.client"})
@SpringBootApplication(scanBasePackages = {"fr.pacifista.api", "com.funixproductions.core"})
public class PacifistaApiService {

    public static void main(final String[] args) {
        SpringApplication.run(PacifistaApiService.class, args);
    }

}
