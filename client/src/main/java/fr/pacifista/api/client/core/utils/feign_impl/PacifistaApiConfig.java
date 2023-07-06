package fr.pacifista.api.client.core.utils.feign_impl;

import com.funixproductions.core.exceptions.ApiException;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public class PacifistaApiConfig {
    private volatile static PacifistaApiConfig instance = null;

    /**
     * https://api.pacifista.fr
     */
    private final String urlDomainPacifistaApi;

    /**
     * https://api.funixproductions.com
     */
    private final String urlDomainFunixProductionsApi;

    private PacifistaApiConfig() throws ApiException {
        final String pacifistaApiDomain = System.getenv("PACIFISTA_API_URL_DOMAIN");
        final String funixProdApiDomain = System.getenv("FUNIXPROD_API_URL_DOMAIN");

        if (Strings.isEmpty(funixProdApiDomain)) {
            this.urlDomainFunixProductionsApi = "https://api.funixproductions.com";
        } else {
            this.urlDomainFunixProductionsApi = funixProdApiDomain;
        }
        if (Strings.isEmpty(pacifistaApiDomain)) {
            this.urlDomainPacifistaApi = "https://api.pacifista.fr";
        } else {
            this.urlDomainPacifistaApi = pacifistaApiDomain;
        }
    }

    public static PacifistaApiConfig getInstance() throws ApiException {
        if (instance == null) {
            instance = new PacifistaApiConfig();
        }

        return instance;
    }
}
