package fr.pacifista.api.web.shop.service.payment.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.payment.entities.ShopArticlePurchase;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopArticlePurchaseRepository extends ApiRepository<ShopArticlePurchase> {
    void deleteAllByArticle(ShopArticle article);
}
