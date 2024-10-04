package fr.pacifista.api.server.warps.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_warps_portals")
public class WarpPortal extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "warp_id", nullable = false)
    private Warp warp;

    @Enumerated(EnumType.STRING)
    @Column(name = "server_type", nullable = false)
    private ServerType serverType;

    @Column(name = "world_id", nullable = false)
    private String worldId;

    @Column(name = "lesser_boundary_corner_x", nullable = false)
    private Integer lesserBoundaryCornerX;

    @Column(name = "lesser_boundary_corner_y", nullable = false)
    private Integer lesserBoundaryCornerY;

    @Column(name = "lesser_boundary_corner_z", nullable = false)
    private Integer lesserBoundaryCornerZ;

    @Column(name = "greater_boundary_corner_x", nullable = false)
    private Integer greaterBoundaryCornerX;

    @Column(name = "greater_boundary_corner_y", nullable = false)
    private Integer greaterBoundaryCornerY;

    @Column(name = "greater_boundary_corner_z", nullable = false)
    private Integer greaterBoundaryCornerZ;

    public UUID getWorldId() {
        if (worldId == null) {
            return null;
        } else {
            return UUID.fromString(worldId);
        }
    }

    public void setWorldId(UUID worldId) {
        if (worldId == null) {
            this.worldId = null;
        } else {
            this.worldId = worldId.toString();
        }
    }
}
