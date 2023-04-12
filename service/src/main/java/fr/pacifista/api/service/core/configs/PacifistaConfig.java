package fr.pacifista.api.service.core.configs;

import fr.funixgaming.api.core.utils.network.IPUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("pacifista.api")
public class PacifistaConfig {

    /**
     * Determines of the API is behind a proxy.
     */
    private boolean appProxied = true;

    @Bean
    public IPUtils ipUtils() {
        return new IPUtils(appProxied);
    }

}
