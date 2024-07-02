package fr.pacifista.api.server.essentials.service.homes.entities;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "player_homes")
public class Home extends Location {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "player_uuid")
    private String playerUuid;

    @Column
    private String material;

    public void setPlayerUuid(UUID playerUuid) {
        if (playerUuid != null) {
            this.playerUuid = playerUuid.toString();
        } else {
            this.playerUuid = null;
        }
    }

    public UUID getPlayerUuid() {
        if (this.playerUuid != null) {
            return UUID.fromString(this.playerUuid);
        } else {
            return null;
        }
    }
}
