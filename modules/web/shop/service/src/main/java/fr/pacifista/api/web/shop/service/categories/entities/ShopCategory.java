package fr.pacifista.api.web.shop.service.categories.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "shop_categories")
public class ShopCategory extends ApiEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "multi_purchase_allowed")
    private Boolean multiPurchaseAllowed;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<ShopArticle> articles;

}
