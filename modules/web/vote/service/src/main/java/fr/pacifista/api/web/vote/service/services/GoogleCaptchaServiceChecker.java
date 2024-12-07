package fr.pacifista.api.web.vote.service.services;

import com.funixproductions.api.google.recaptcha.client.clients.GoogleRecaptchaInternalClient;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.tools.network.IPUtils;
import com.google.common.base.Strings;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleCaptchaServiceChecker {

    private final GoogleRecaptchaInternalClient recaptchaClient;
    private final HttpServletRequest request;
    private final IPUtils ipUtils;

    public void checkCaptcha() {
        final String recaptchaToken = request.getHeader("X-Captcha-Google-Code");
        if (Strings.isNullOrEmpty(recaptchaToken)) {
            throw new ApiBadRequestException("Token google manquant");
        }

        try {
            recaptchaClient.verify("VOTE_PACIFISTA", recaptchaToken, ipUtils.getClientIp(request));
        } catch (FeignException e) {
            throw new ApiBadRequestException("Token google invalide");
        }
    }

}
