package fr.pacifista.api.server.shop.service.entities.admin_shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "admin_shop_player_limit")
public class AdminShopPlayerLimit extends AdminShopDataWithCategory {

    @Column(nullable = false, name = "player_id")
    private String playerId;

    @Column(nullable = false, name = "money_generated")
    private Double moneyGenerated;

    public UUID getPlayerId() {
        if (playerId == null) {
            return null;
        } else {
            return UUID.fromString(playerId);
        }
    }

    public void setPlayerId(UUID playerId) {
        if (playerId == null) {
            this.playerId = null;
        } else {
            this.playerId = playerId.toString();
        }
    }
}
