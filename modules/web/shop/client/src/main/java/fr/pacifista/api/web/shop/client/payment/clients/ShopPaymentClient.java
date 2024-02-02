package fr.pacifista.api.web.shop.client.payment.clients;

import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ShopPaymentClient {

    @PostMapping
    PacifistaShopPaymentResponseDTO createOrder(@RequestBody @Valid PacifistaShopPaymentRequestDTO pacifistaShopPaymentRequestDTO);

    @GetMapping
    PacifistaShopPaymentResponseDTO getPaymentStatus(@RequestParam String paymentExternalOrderId);

    @PostMapping("/capture")
    PacifistaShopPaymentResponseDTO capturePayment(@RequestParam String paymentExternalOrderId);

}
