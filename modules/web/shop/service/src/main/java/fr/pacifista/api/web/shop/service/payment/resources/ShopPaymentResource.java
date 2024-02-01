package fr.pacifista.api.web.shop.service.payment.resources;

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
        return shopPaymentService.createOrder(pacifistaShopPaymentRequestDTO);
    }

    @Override
    public PacifistaShopPaymentResponseDTO getPaymentStatus(String paymentExternalOrderId) {
        return shopPaymentService.getPaymentStatus(paymentExternalOrderId);
    }

    @Override
    public PacifistaShopPaymentResponseDTO capturePayment(String paymentExternalOrderId) {
        return shopPaymentService.capturePayment(paymentExternalOrderId);
    }

}
