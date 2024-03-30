package fr.pacifista.api.server.claim.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.server.claim.client.enums.ClaimDataUserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "claim_data_user")
public class ClaimDataUser extends ApiEntity {

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @ManyToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private ClaimData claim;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimDataUserRole role;

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
