package fr.pacifista.api.web.shop.client.payment.dtos;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaShopPaymentResponseDTO extends ApiDTO {

    private String paymentExternalOrderId;

    private PaymentType paymentType;

    private String userId;

    //-- EXTERNAL API DATA --//

    private Boolean orderPaid;

    private String urlClientRedirection;

}
