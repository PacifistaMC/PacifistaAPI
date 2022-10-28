package fr.pacifista.api.client.core.utils.feign_impl;

import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;

class FunixApiInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
