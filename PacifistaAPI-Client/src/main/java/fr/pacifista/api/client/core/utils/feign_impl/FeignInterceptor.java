package fr.pacifista.api.client.core.utils.feign_impl;

import com.google.common.net.HttpHeaders;
import feign.Feign;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.funixgaming.api.client.user.clients.UserAuthClient;
import fr.funixgaming.api.client.user.dtos.UserTokenDTO;
import fr.funixgaming.api.client.user.dtos.requests.UserLoginDTO;
import fr.funixgaming.api.core.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.time.Instant;
import java.util.Date;

@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    private final PacifistaApiConfig pacifistaApiConfig;
    private final UserAuthClient authClient;

    private UserTokenDTO tokenCache;

    protected FeignInterceptor(PacifistaApiConfig pacifistaApiConfig) {
        this.pacifistaApiConfig = pacifistaApiConfig;

        this.authClient = Feign.builder()
                .requestInterceptor(new FunixApiInterceptor())
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(UserAuthClient.class, String.format("%s/user", pacifistaApiConfig.getUrlDomainFunixApi()));
    }

    @Override
    public void apply(RequestTemplate template) {
        try {
            final UserTokenDTO token = getToken();

            template.header(HttpHeaders.CONTENT_TYPE, "application/json");
            template.header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token.getToken()));
        } catch (ApiException e) {
            log.error("Erreur récupération token API Pacifista. Message: {}", e.getMessage());
        }
    }

    private UserTokenDTO getToken() throws ApiException {
        final UserTokenDTO tokenDTO;

        if (isTokenValid()) {
            tokenDTO = tokenCache;
        } else {
            tokenDTO = generateNewToken();
            log.info("Nouveau token pour la FunixAPI généré.");
        }

        if (tokenDTO == null) {
            throw new ApiException("Le token d'auth pour la PacifistaAPI est invalide.");
        } else {
            tokenCache = tokenDTO;
            return tokenDTO;
        }
    }

    private boolean isTokenValid() throws ApiException {
        try {
            final Date now = Date.from(Instant.now().minusSeconds(30));

            if (tokenCache == null || tokenCache.getExpirationDate().before(now)) {
                return false;
            } else {
                this.authClient.valid(tokenCache.getToken());
                return true;
            }
        } catch (FeignException e) {
            if (Integer.toString(e.status()).startsWith("5")) {
                throw new ApiException(String.format("Le serveur d'auth rencontre une erreur. Code: %d Message: %s", e.status(), e.getMessage()));
            } else {
                return false;
            }
        }
    }

    private UserTokenDTO generateNewToken() throws ApiException {
        try {
            final UserLoginDTO loginDTO = new UserLoginDTO();
            loginDTO.setUsername(this.pacifistaApiConfig.getUsernameApi());
            loginDTO.setPassword(this.pacifistaApiConfig.getPasswordApi());

            return this.authClient.login(loginDTO, null);
        } catch (FeignException e) {
            if (Integer.toString(e.status()).startsWith("5")) {
                throw new ApiException(String.format("Le serveur d'auth rencontre une erreur. Code: %d Message: %s", e.status(), e.getMessage()));
            } else {
                throw new ApiException(String.format("Vos identifiants sont invalides. Code erreur: %d Body: %s", e.status(), e.getMessage()));
            }
        }
    }
}
