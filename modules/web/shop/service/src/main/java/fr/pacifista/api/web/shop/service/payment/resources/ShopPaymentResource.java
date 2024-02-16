package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.core.exceptions.ApiException;
import fr.pacifista.api.web.shop.client.payment.clients.ShopPaymentClient;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.payment.services.ShopPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/shop")
@RequiredArgsConstructor
public class ShopPaymentResource implements ShopPaymentClient {

    private final ShopPaymentService shopPaymentService;

    @Override
    public PacifistaShopPaymentResponseDTO createOrder(PacifistaShopPaymentRequestDTO pacifistaShopPaymentRequestDTO) {
        try {
            return shopPaymentService.createOrder(pacifistaShopPaymentRequestDTO);
        } catch (ApiException apiException) {
            throw apiException;
        } catch (Exception exception) {
            throw new ApiException("Erreur interne lors de la création d'une commande.", exception);
        }
    }

    @Override
    public PacifistaShopPaymentResponseDTO getPaymentStatus(String paymentExternalOrderId) {
        try {
            return shopPaymentService.getPaymentStatus(paymentExternalOrderId);
        } catch (ApiException apiException) {
            throw apiException;
        } catch (Exception exception) {
            throw new ApiException("Erreur interne lors de la récupération du statut d'une commande.", exception);
        }
    }

    @Override
    public PacifistaShopPaymentResponseDTO capturePayment(String paymentExternalOrderId) {
        try {
            return shopPaymentService.capturePayment(paymentExternalOrderId);
        } catch (ApiException apiException) {
            throw apiException;
        } catch (Exception exception) {
            throw new ApiException("Erreur interne lors de la capture d'une commande.", exception);
        }
    }

}
