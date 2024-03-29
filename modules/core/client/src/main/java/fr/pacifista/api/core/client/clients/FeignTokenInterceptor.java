package fr.pacifista.api.core.client.clients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class FeignTokenInterceptor implements RequestInterceptor {

    private final String barerToken = System.getenv("FUNIXPRODUCTIONS_API_TOKEN");

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + barerToken);
    }
}
