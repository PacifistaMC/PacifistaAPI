package fr.pacifista.api.web.shop.service.payment.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPaymentRepository extends ApiRepository<ShopPayment> {
}
