package fr.pacifista.api.client.core.utils.feign_impl;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

public class FeignTokenInterceptor implements RequestInterceptor {

    private final String barerToken = System.getenv("FUNIXPRODUCTIONS_API_TOKEN");

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + barerToken);
    }
}
