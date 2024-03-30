package fr.pacifista.api.server.claim.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "user_claim_amount")
public class UserClaimAmount extends ApiEntity {

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "blocks_amount", nullable = false)
    private Integer blocksAmount;

    public UUID getPlayerId() {
        if (this.playerId == null) {
            return null;
        } else {
            return UUID.fromString(this.playerId);
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
