package fr.pacifista.api.server.essentials.service.ignore_players.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "player_ignores")
public class PlayerIgnore extends ApiEntity {

    @Column(nullable = false, name = "player_id")
    private String playerId;

    @Column(nullable = false, name = "ignored_player_id")
    private String ignoredPlayerId;

    public UUID getPlayerUuid() {
        if (this.playerId != null) {
            return UUID.fromString(this.playerId);
        } else {
            return null;
        }
    }

    public void setPlayerUuid(UUID playerUuid) {
        if (playerUuid != null) {
            this.playerId = playerUuid.toString();
        } else {
            this.playerId = null;
        }
    }

    public UUID getIgnoredPlayerUuid() {
        if (this.ignoredPlayerId != null) {
            return UUID.fromString(this.ignoredPlayerId);
        } else {
            return null;
        }
    }

    public void setIgnoredPlayerUuid(UUID ignoredPlayerUuid) {
        if (ignoredPlayerUuid != null) {
            this.ignoredPlayerId = ignoredPlayerUuid.toString();
        } else {
            this.ignoredPlayerId = null;
        }
    }

}
