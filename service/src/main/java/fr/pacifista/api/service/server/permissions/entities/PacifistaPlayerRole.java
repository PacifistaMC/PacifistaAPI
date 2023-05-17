package fr.pacifista.api.service.server.permissions.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_player_role")
public class PacifistaPlayerRole extends ApiEntity {

    @Column(name = "player_uuid", nullable = false, updatable = false)
    private String playerUuid;

    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    private PacifistaRole role;

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid.toString();
    }

    public UUID getPlayerUuid() {
        if (playerUuid == null) {
            return null;
        } else {
            return UUID.fromString(playerUuid);
        }
    }
}
