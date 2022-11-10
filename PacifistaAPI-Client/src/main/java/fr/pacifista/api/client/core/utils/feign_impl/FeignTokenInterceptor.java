package fr.pacifista.api.client.core.utils.feign_impl;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * Class used to access api without granting real token.
 * This is why when we access funix api in local host we are auth with an admin account
 */
public class FeignTokenInterceptor implements RequestInterceptor {
    /**
     * Inject fake token header to provide auth and get admin rights
     * If we don't do that we can't fake whitelist pass
     * @param requestTemplate template
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer token");
    }
}
