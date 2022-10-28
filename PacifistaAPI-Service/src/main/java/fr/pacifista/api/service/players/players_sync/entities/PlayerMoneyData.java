package fr.pacifista.api.service.players.players_sync.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_player_money_data")
public class PlayerMoneyData extends ApiEntity {

    @Column(name = "player_owner_uuid", nullable = false)
    private String playerOwnerUuid;

    @Column(nullable = false)
    private Double money;

    public UUID getPlayerOwnerUuid() {
        if (playerOwnerUuid == null) {
            return null;
        } else {
            return UUID.fromString(playerOwnerUuid);
        }
    }

    public void setPlayerOwnerUuid(UUID playerOwnerUuid) {
        this.playerOwnerUuid = playerOwnerUuid.toString();
    }

}
