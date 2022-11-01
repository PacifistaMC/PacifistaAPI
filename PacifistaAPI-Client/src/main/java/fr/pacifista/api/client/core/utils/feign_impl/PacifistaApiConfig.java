package fr.pacifista.api.client.core.utils.feign_impl;

import fr.funixgaming.api.core.exceptions.ApiException;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public class PacifistaApiConfig {
    private volatile static PacifistaApiConfig instance = null;

    /**
     * https://api.pacifista.fr
     */
    private final String urlDomainPacifistaApi = System.getenv("PACIFISTA_API_URL_DOMAIN");

    /**
     * https://api.funixgaming.fr
     */
    private final String urlDomainFunixApi = System.getenv("FUNIX_API_URL_DOMAIN");

    /**
     * username to access api
     */
    private final String usernameApi = System.getenv("FUNIX_API_USERNAME");

    /**
     * password to access api
     */
    private final String passwordApi = System.getenv("FUNIX_API_PASSWORD");

    private PacifistaApiConfig() throws ApiException {
        if (Strings.isEmpty(urlDomainPacifistaApi)) {
            throw new ApiException("PACIFISTA_API_URL_DOMAIN is not set");
        } else if (Strings.isEmpty(urlDomainFunixApi)) {
            throw new ApiException("FUNIX_API_URL_DOMAIN is not set");
        } else if (Strings.isEmpty(usernameApi)) {
            throw new ApiException("FUNIX_API_USERNAME is not set");
        } else if (Strings.isEmpty(passwordApi)) {
            throw new ApiException("FUNIX_API_PASSWORD is not set");
        }
    }

    public static PacifistaApiConfig getInstance() throws ApiException {
        if (instance == null) {
            instance = new PacifistaApiConfig();
        }

        return instance;
    }
}