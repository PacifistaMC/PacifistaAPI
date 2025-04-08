package fr.pacifista.api.web.shop.service.categories.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ShopArticle> articles;

}
