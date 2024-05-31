package fr.pacifista.api.server.shop.service.entities.shop;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "player_chest_shop")
public class PlayerChestShop extends Location {

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "item_serialized")
    private String itemSerialized;

    @Column(nullable = false, name = "price_buy")
    private Double priceBuy;

    @Column(nullable = false, name = "price_sell")
    private Double priceSell;

}
