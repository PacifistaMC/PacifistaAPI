package fr.pacifista.api.web.shop.service.payment.resources;

import fr.pacifista.api.web.shop.client.payment.clients.ShopPaymentClient;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.payment.services.ShopPaymentCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/shop")
@RequiredArgsConstructor
public class ShopPaymentResource implements ShopPaymentClient {

    private final ShopPaymentCrudService shopPaymentCrudService;

    @Override
    public PacifistaShopPaymentResponseDTO createOrder(PacifistaShopPaymentRequestDTO pacifistaShopPaymentRequestDTO) {
        return shopPaymentCrudService.createOrder(pacifistaShopPaymentRequestDTO);
    }

    @Override
    public PacifistaShopPaymentResponseDTO getPaymentStatus(String paymentExternalOrderId) {
        return shopPaymentCrudService.getPaymentStatus(paymentExternalOrderId);
    }

    @Override
    public PacifistaShopPaymentResponseDTO capturePayment(String paymentExternalOrderId) {
        return shopPaymentCrudService.capturePayment(paymentExternalOrderId);
    }

}
