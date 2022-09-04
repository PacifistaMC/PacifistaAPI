package fr.pacifista.api.service.warps.entities;

import fr.pacifista.api.client.warps.enums.WarpType;
import fr.pacifista.api.service.core.entities.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_warps")
public class Warp extends Location {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "warp_item", length = 10000)
    private String warpItem;

    @Column(nullable = false)
    private Boolean accessible;

    @Column(nullable = false, name = "player_owner_uuid")
    private String playerOwnerUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarpType type;

    public void setPlayerOwnerUuid(UUID playerOwnerUuid) {
        if (playerOwnerUuid != null) {
            this.playerOwnerUuid = playerOwnerUuid.toString();
        } else {
            this.playerOwnerUuid = null;
        }
    }

    public UUID getPlayerOwnerUuid() {
        if (this.playerOwnerUuid != null) {
            return UUID.fromString(this.playerOwnerUuid);
        } else {
            return null;
        }
    }
}
