package fr.pacifista.api.core.service.configs;

import com.funixproductions.core.tools.network.IPUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class PacifistaCoreConfig {

    /**
     * Determines of the API is behind a proxy.
     */
    private boolean appProxied = true;

    @Bean
    public IPUtils ipUtils() {
        return new IPUtils(appProxied);
    }

}
