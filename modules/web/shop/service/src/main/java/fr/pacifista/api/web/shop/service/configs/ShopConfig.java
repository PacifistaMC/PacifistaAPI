package fr.pacifista.api.web.shop.service.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("pacifista.api.shop")
public class ShopConfig {

    /**
     * The url of the shop when no errors
     */
    private String returnUrl = "https://pacifista.fr/shop/checkout/check";

    /**
     * The url of the shop when cancel
     */
    private String cancelUrl = "https://pacifista.fr/shop/checkout/cancel";

}
