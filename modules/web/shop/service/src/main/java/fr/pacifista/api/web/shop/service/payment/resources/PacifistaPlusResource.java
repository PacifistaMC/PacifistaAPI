package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalSubscriptionDTO;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/shop/pacifistaplus")
@RequiredArgsConstructor
public class PacifistaPlusResource {

    private final PacifistaPlusService pacifistaPlusService;
    private final CurrentSession currentSession;

    @PostMapping
    public PaypalSubscriptionDTO newSubscription() {
        return this.pacifistaPlusService.newSubscription(
                this.getUser().getId()
        );
    }

    @GetMapping
    public PaypalSubscriptionDTO getSubscription() {
        return this.pacifistaPlusService.getSubscription(
                this.getUser().getId()
        );
    }

    @PostMapping("/pause")
    public PaypalSubscriptionDTO pauseSubscription() {
        return this.pacifistaPlusService.pauseSubscription(
                this.getUser().getId()
        );
    }

    @PostMapping("/resume")
    public PaypalSubscriptionDTO resumeSubscription() {
        return this.pacifistaPlusService.resumeSubscription(
                this.getUser().getId()
        );
    }

    @PostMapping("/cancel")
    public void cancelSubscription() {
        this.pacifistaPlusService.cancelSubscription(
                this.getUser().getId()
        );
    }

    @NonNull
    private UserDTO getUser() {
        final UserDTO userDTO = this.currentSession.getCurrentUser();

        if (userDTO == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour effectuer cette action.");
        } else {
            return userDTO;
        }
    }

}
