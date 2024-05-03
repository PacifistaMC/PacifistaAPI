package fr.pacifista.api.server.shop.service.entities.admin_shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "admin_shop_item")
public class AdminShopItem extends AdminShopDataWithCategory {

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private Double price;

}
