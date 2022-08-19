package fr.pacifista.api.service.permissions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
