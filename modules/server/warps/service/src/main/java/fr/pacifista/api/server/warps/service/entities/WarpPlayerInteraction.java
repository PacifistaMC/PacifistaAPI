package fr.pacifista.api.server.warps.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.server.warps.client.enums.WarpInteractionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_warps_players_interactions")
public class WarpPlayerInteraction extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "warp_id", nullable = false)
    private Warp warp;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_type", nullable = false)
    private WarpInteractionType interactionType;

    public void setPlayerId(UUID playerId) {
        if (playerId != null) {
            this.playerId = playerId.toString();
        } else {
            this.playerId = null;
        }
    }

    public UUID getPlayerId() {
        if (this.playerId != null) {
            return UUID.fromString(this.playerId);
        } else {
            return null;
        }
    }

}

