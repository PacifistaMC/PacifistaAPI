package fr.pacifista.api.web.shop.client.payment.dtos;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalOrderDTO;
import com.funixproductions.api.payment.paypal.client.enums.OrderStatus;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacifistaShopPaymentResponseDTO extends ApiDTO {

    public PacifistaShopPaymentResponseDTO(PaypalOrderDTO paypalOrderDTO, UserDTO userDTO) {
        this.paymentExternalOrderId = paypalOrderDTO.getOrderId();
        this.paymentType = Boolean.TRUE.equals(paypalOrderDTO.getCreditCardPayment()) ? PaymentType.CREDIT_CARD : PaymentType.PAYPAL;
        this.userId = userDTO.getId().toString();
        this.orderPaid = OrderStatus.COMPLETED.equals(paypalOrderDTO.getStatus());
        this.urlClientRedirection = paypalOrderDTO.getUrlClientRedirection();
    }

    private String paymentExternalOrderId;

    private PaymentType paymentType;

    private String userId;

    //-- EXTERNAL API DATA --//

    private Boolean orderPaid;

    private String urlClientRedirection;

}
