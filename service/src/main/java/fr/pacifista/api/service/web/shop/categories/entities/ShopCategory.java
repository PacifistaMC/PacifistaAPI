package fr.pacifista.api.service.web.shop.categories.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_categories")
public class ShopCategory extends ApiEntity {

    @Column(nullable = false)
    private String name;

}
