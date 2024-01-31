package fr.pacifista.api.web.shop.service.payment.mappers;

import com.funixproductions.api.payment.paypal.client.dtos.requests.PaymentDTO;
import com.funixproductions.api.payment.paypal.client.dtos.requests.card.CreditCardPaymentDTO;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopPaymentMapper extends ApiMapper<ShopPayment, PacifistaShopPaymentResponseDTO> {

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "urlClientRedirection", ignore = true)
    @Mapping(target = "orderPaid", ignore = true)
    PacifistaShopPaymentResponseDTO toDto(ShopPayment entity);

    @Mapping(target = "cancelUrl", ignore = true)
    @Mapping(target = "returnUrl", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "purchaseUnits", ignore = true)
    @Mapping(target = "vatInformation", ignore = true)
    @Mapping(target = "originRequest", ignore = true)
    @Mapping(target = "billingAddress", ignore = true)
    CreditCardPaymentDTO toCreditCardPaymentDto(PacifistaShopPaymentRequestDTO.CreditCard creditCard);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userEmail", source = "email")
    PaymentDTO.UserPaymentDTO toUserPaymentDto(UserDTO dto);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "postalCode", ignore = true)
    @Mapping(target = "state", source = "country.name")
    @Mapping(target = "countryCode", source = "country.countryCode2Chars")
    PaymentDTO.BillingAddressDTO toBillingAddressDto(UserDTO dto);

    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "tax", ignore = true)
    PaymentDTO.PurchaseUnitDTO.Item toPaypalItem(ShopArticle article);
}
