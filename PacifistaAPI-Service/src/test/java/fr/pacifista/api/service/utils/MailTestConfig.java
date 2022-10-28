package fr.pacifista.api.service.utils;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MailTestConfig {

    @Bean
    public GreenMail greenMail() {
        return new GreenMail(ServerSetupTest.SMTP)
                .withConfiguration(
                        GreenMailConfiguration.aConfig().withDisabledAuthentication()
                );
    }

}
