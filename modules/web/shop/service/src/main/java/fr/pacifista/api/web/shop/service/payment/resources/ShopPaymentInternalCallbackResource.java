package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalSubscriptionDTO;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kubeinternal/web/shop/cb")
@RequiredArgsConstructor
public class ShopPaymentInternalCallbackResource {

    private final PacifistaPlusService pacifistaPlusService;

    @PostMapping("/paypal-subscription")
    void paymentPaypalSubscriptionCallback(@RequestBody PaypalSubscriptionDTO subscriptionDTO) {
        if (PacifistaPlusService.PROJECT_NAME.equals(subscriptionDTO.getPlan().getProjectName()) && PacifistaPlusService.SUBSCRIPTION_NAME.equals(subscriptionDTO.getPlan().getName())) {
            this.pacifistaPlusService.onNewPaypalSubscription(subscriptionDTO.getFunixProdUserId(), subscriptionDTO.getNextPaymentDate());
        }
    }

}
