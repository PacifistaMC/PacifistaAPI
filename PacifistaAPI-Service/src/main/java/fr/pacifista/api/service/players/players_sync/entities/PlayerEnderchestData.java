package fr.pacifista.api.service.players.players_sync.entities;

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
@Entity(name = "pacifista_player_enderchest_data")
public class PlayerEnderchestData extends ApiEntity {

    @Column(name = "player_owner_uuid", nullable = false)
    private String playerOwnerUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private ServerGameMode gameMode;

    @Column(name = "enderchest_serialized", nullable = false, length = 100000)
    private String enderchestSerialized;

    @Column(name = "enderchest_paladin_serialized", nullable = false, length = 100000)
    private String enderchestPaladinSerialized;

    @Column(name = "enderchest_elite_serialized", nullable = false, length = 100000)
    private String enderchestEliteSerialized;

    @Column(name = "enderchest_legendaire_serialized", nullable = false, length = 100000)
    private String enderchestLegendaireSerialized;

    @Column(name = "enderchest_mine_serialized", nullable = false, length = 100000)
    private String enderchestMineSerialized;

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
