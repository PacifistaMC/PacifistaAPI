package fr.pacifista.api.service.google.recaptcha;

import com.funixproductions.api.client.google.recaptcha.clients.GoogleCaptchaClient;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.tools.network.IPUtils;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleCaptchaService {

    private final GoogleCaptchaClient googleCaptchaClient;
    private final IPUtils ipUtils;

    public void checkCode(final HttpServletRequest servletRequest, final String action) {
        try {
            this.googleCaptchaClient.verify(
                    servletRequest.getHeader("X-Captcha-Google-Code"),
                    action,
                    this.ipUtils.getClientIp(servletRequest)
            );
        } catch (FeignException e) {
            final String httpCode = Integer.toString(e.status());

            if (httpCode.startsWith("5")) {
                throw new ApiException("La FunixProductions API a rencontré une erreur interne. (Erreur " + httpCode + ")");
            } else if (httpCode.startsWith("4")) {
                throw new ApiBadRequestException("Le code de vérification recaptcha est invalide. (Erreur code " + httpCode + ", message " + e.getMessage() + ")");
            } else {
                throw new ApiException("La FunixProductions API a rencontré une erreur inconnue. (Erreur " + httpCode + ")");
            }
        }
    }

}
