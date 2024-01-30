package fr.pacifista.api.web.shop.service.payment.entities;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_payment")
public class ShopPayment extends ApiEntity {

    @Column(name = "payment_external_order_id")
    private String paymentExternalOrderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_type")
    private PaymentType paymentType;

    @Column(nullable = false, name = "user_id")
    private String userId;

}
