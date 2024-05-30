package fr.pacifista.api.server.shop.service.entities.shop;

import fr.pacifista.api.core.service.entities.MinecraftPlayer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "player_shop_item")
public class PlayerShopItem extends MinecraftPlayer {

    @Column(name = "item_serialized", nullable = false)
    private String itemSerialized;

    @Column(nullable = false)
    private Double price;

    @Column(name = "sold_at")
    private Date soldAt;

    @Column(name = "buyer_name")
    private String buyerName;

}
