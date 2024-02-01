package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.api.payment.paypal.client.clients.PaypalOrderFeignClient;
import com.funixproductions.api.payment.paypal.client.dtos.requests.PaymentDTO;
import com.funixproductions.api.payment.paypal.client.dtos.requests.card.CreditCardPaymentDTO;
import com.funixproductions.api.payment.paypal.client.dtos.requests.paypal.PaypalPaymentDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalOrderDTO;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.tools.pdf.tools.VATInformation;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.configs.ShopConfig;
import fr.pacifista.api.web.shop.service.payment.mappers.ShopPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaypalPaymentService {

    private final ShopConfig shopConfig;
    private final PaypalOrderFeignClient paypalOrderClient;
    private final ShopPaymentMapper shopPaymentMapper;

    public PaypalOrderDTO createOrder(final Map<ShopArticle, Integer> articles,
                                      final UserDTO userDTO,
                                      final UUID paymentId) {
        try {
            final PaypalPaymentDTO paypalPaymentDTO = new PaypalPaymentDTO();

            paypalPaymentDTO.setBrandName("Pacifista");
            fillPaymentDto(paypalPaymentDTO, articles, userDTO, paymentId);
            return paypalOrderClient.createPaypalOrder(paypalPaymentDTO);
        } catch (Exception e) {
            throw new ApiException("Impossible de créer la commande paypal.", e);
        }
    }

    public PaypalOrderDTO createOrder(final Map<ShopArticle, Integer> articles,
                                      final UserDTO userDTO,
                                      final UUID paymentId,
                                      final PacifistaShopPaymentRequestDTO.CreditCard creditCard) {
        try {
            final CreditCardPaymentDTO creditCardPaymentDTO = this.shopPaymentMapper.toCreditCardPaymentDto(creditCard);

            fillPaymentDto(creditCardPaymentDTO, articles, userDTO, paymentId);
            return paypalOrderClient.createCardOrder(creditCardPaymentDTO);
        } catch (Exception e) {
            throw new ApiException("Impossible de créer la commande par carte bleue.", e);
        }
    }

    public PaypalOrderDTO getOrder(final String orderId) {
        try {
            return paypalOrderClient.getOrder(orderId);
        } catch (Exception e) {
            throw new ApiException("Impossible de récupérer la commande paypal.", e);
        }
    }

    public PaypalOrderDTO captureOrder(final String orderId) {
        try {
            return paypalOrderClient.captureOrder(orderId);
        } catch (Exception e) {
            throw new ApiException("Impossible de capturer la commande paypal.", e);
        }
    }

    private void fillPaymentDto(final PaymentDTO toFill, final Map<ShopArticle, Integer> articles, final UserDTO userDTO, final UUID paymentId) {
        final VATInformation vat = getVatFromUser(userDTO);

        toFill.setCancelUrl(shopConfig.getCancelUrl());
        toFill.setReturnUrl(shopConfig.getReturnUrl());
        toFill.setUser(this.shopPaymentMapper.toUserPaymentDto(userDTO));
        toFill.setPurchaseUnits(mapToPurchasesUnit(articles, vat == null ? 0 : vat.getVatRate(), paymentId));
        toFill.setVatInformation(vat);
        toFill.setOriginRequest("Pacifista");
        toFill.setBillingAddress(this.shopPaymentMapper.toBillingAddressDto(userDTO));
    }

    private List<PaymentDTO.PurchaseUnitDTO> mapToPurchasesUnit(final Map<ShopArticle, Integer> articles, double vatAmount, final UUID paymentId) {
        final PaymentDTO.PurchaseUnitDTO purchaseUnitDTO = new PaymentDTO.PurchaseUnitDTO();

        purchaseUnitDTO.setCustomId(paymentId.toString());
        purchaseUnitDTO.setSoftDescriptor("shop pacifista.fr");
        purchaseUnitDTO.setDescription("Achat sur la boutique de Pacifista.fr le serveur Minecraft.");

        final List<PaymentDTO.PurchaseUnitDTO.Item> items = purchaseUnitDTO.getItems();
        for (Map.Entry<ShopArticle, Integer> entry : articles.entrySet()) {
            final PaymentDTO.PurchaseUnitDTO.Item item = this.shopPaymentMapper.toPaypalItem(entry.getKey());

            item.setQuantity(entry.getValue());
            if (vatAmount > 0) {
                item.setTax(item.getPrice() * vatAmount);
            }
            items.add(item);
        }
        purchaseUnitDTO.setItems(items);

        return Collections.singletonList(purchaseUnitDTO);
    }
    private VATInformation getVatFromUser(final UserDTO userDTO) {
        for (final VATInformation vat : VATInformation.values()) {
            if (vat.getCountryCode().equalsIgnoreCase(userDTO.getCountry().getCountryCode2Chars())) {
                return vat;
            }
        }
        return VATInformation.FRANCE;
    }

}
