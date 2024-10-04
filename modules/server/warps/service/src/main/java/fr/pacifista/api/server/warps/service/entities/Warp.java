package fr.pacifista.api.server.warps.service.entities;

import fr.pacifista.api.core.service.entities.Location;
import fr.pacifista.api.server.warps.client.enums.WarpType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_warps")
public class Warp extends Location {
    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "warp_item")
    private String warpItem;

    @Column(nullable = false, name = "public_access")
    private Boolean publicAccess = true;

    @Column(nullable = false, name = "player_owner_uuid")
    private String playerOwnerUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
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
