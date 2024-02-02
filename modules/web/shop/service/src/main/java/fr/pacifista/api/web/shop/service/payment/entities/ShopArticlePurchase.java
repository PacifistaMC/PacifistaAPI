package fr.pacifista.api.web.shop.service.payment.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_article_purchases")
public class ShopArticlePurchase extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private ShopPayment payment;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private ShopArticle article;

    @Column(nullable = false)
    private Integer quantity;

}
