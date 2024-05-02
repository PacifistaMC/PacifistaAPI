package fr.pacifista.api.web.shop.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
        "com.funixproductions.api.user",
        "com.funixproductions.api.payment.paypal",
        "fr.pacifista",
})
@SpringBootApplication(scanBasePackages = {
        "com.funixproductions",
        "fr.pacifista"
})
public class PacifistaWebShopApp {
    public static void main(String[] args) {
        SpringApplication.run(PacifistaWebShopApp.class, args);
    }
}
