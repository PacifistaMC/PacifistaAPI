package fr.pacifista.api.service.server.players.players_sync.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_player_experience_data")
public class PlayerExperienceData extends ApiEntity {

    @Column(name = "player_owner_uuid", nullable = false)
    private String playerOwnerUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private ServerGameMode gameMode;

    @Column(name = "total_experience", nullable = false)
    private Integer totalExperience;

    @Column(nullable = false)
    private Float exp;

    @Column(nullable = false)
    private Integer level;

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
