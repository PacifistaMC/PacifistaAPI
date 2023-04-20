package fr.pacifista.api.service.server.boxes.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_player_box")
public class PlayerBox extends ApiEntity {

    @Column(name = "player_uuid", nullable = false)
    private String playerUuid;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(nullable = false)
    private Integer amount;

    @Nullable
    public UUID getPlayerUuid() {
        if (playerUuid == null) {
            return null;
        } else {
            return UUID.fromString(playerUuid);
        }
    }

    public void setPlayerUuid(@NotNull final UUID playerUuid) {
        this.playerUuid = playerUuid.toString();
    }
}
