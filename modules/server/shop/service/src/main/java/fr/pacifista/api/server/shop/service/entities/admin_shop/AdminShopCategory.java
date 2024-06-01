package fr.pacifista.api.server.shop.service.entities.admin_shop;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "admin_shop_category")
public class AdminShopCategory extends ApiEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, name = "money_sell_limit")
    private Double moneySellLimit;

    @Column(nullable = false, name = "item_menu_material")
    private String itemMenuMaterial;

}
