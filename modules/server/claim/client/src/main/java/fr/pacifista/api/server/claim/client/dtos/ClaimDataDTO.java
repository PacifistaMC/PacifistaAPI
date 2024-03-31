package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDataDTO extends ApiDTO {

    @Nullable
    private ClaimDataDTO parent;

    @NotNull(message = "serverType is required")
    private ServerType serverType;

    @NotNull(message = "worldId is required")
    private UUID worldId;

    @NotNull(message = "lesserBoundaryCornerX is required")
    private Double lesserBoundaryCornerX;

    @NotNull(message = "lesserBoundaryCornerZ is required")
    private Double lesserBoundaryCornerZ;

    @NotNull(message = "greaterBoundaryCornerX is required")
    private Double greaterBoundaryCornerX;

    @NotNull(message = "greaterBoundaryCornerZ is required")
    private Double greaterBoundaryCornerZ;

    private ClaimDataConfigDTO config;

    private List<ClaimDataUserDTO> users;

    public ClaimDataDTO(final ServerType serverType,
                        final UUID worldId,
                        final Double lesserBoundaryCornerX,
                        final Double lesserBoundaryCornerZ,
                        final Double greaterBoundaryCornerX,
                        final Double greaterBoundaryCornerZ) {
        this.serverType = serverType;
        this.worldId = worldId;
        this.lesserBoundaryCornerX = lesserBoundaryCornerX;
        this.lesserBoundaryCornerZ = lesserBoundaryCornerZ;
        this.greaterBoundaryCornerX = greaterBoundaryCornerX;
        this.greaterBoundaryCornerZ = greaterBoundaryCornerZ;
    }

    public ClaimDataDTO(@Nullable final ClaimDataDTO parent,
                        final ServerType serverType,
                        final UUID worldId,
                        final Double lesserBoundaryCornerX,
                        final Double lesserBoundaryCornerZ,
                        final Double greaterBoundaryCornerX,
                        final Double greaterBoundaryCornerZ) {
        this.parent = parent;
        this.serverType = serverType;
        this.worldId = worldId;
        this.lesserBoundaryCornerX = lesserBoundaryCornerX;
        this.lesserBoundaryCornerZ = lesserBoundaryCornerZ;
        this.greaterBoundaryCornerX = greaterBoundaryCornerX;
        this.greaterBoundaryCornerZ = greaterBoundaryCornerZ;
    }

    public double getClaimCost(final double blocPrice) {
        return (greaterBoundaryCornerX - lesserBoundaryCornerX) * (greaterBoundaryCornerZ - lesserBoundaryCornerZ) * blocPrice;
    }

    public void setWorldId(String worldId) {
        if (worldId == null) {
            this.worldId = null;
        } else {
            this.worldId = UUID.fromString(worldId);
        }
    }

    public String getWorldId() {
        if (this.worldId == null) {
            return null;
        } else {
            return this.worldId.toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final ClaimDataDTO other) {
            final boolean similar = serverType.equals(other.serverType) &&
                    worldId.equals(other.worldId) &&
                    lesserBoundaryCornerX.equals(other.lesserBoundaryCornerX) &&
                    lesserBoundaryCornerZ.equals(other.lesserBoundaryCornerZ) &&
                    greaterBoundaryCornerX.equals(other.greaterBoundaryCornerX) &&
                    greaterBoundaryCornerZ.equals(other.greaterBoundaryCornerZ);
            final UUID id = getId();

            if (id == null) {
                return similar;
            } else {
                return similar && id.equals(other.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int hash = getId() == null ? 0 : getId().hashCode();
        return 13 + this.serverType.hashCode() +
                this.worldId.hashCode() +
                this.lesserBoundaryCornerX.hashCode() +
                this.lesserBoundaryCornerZ.hashCode() +
                this.greaterBoundaryCornerX.hashCode() +
                this.greaterBoundaryCornerZ.hashCode() +
                hash;
    }

    @Override
    public String toString() {
        return "ClaimDataDTO{" +
                "serverType=" + serverType +
                ", worldId=" + worldId +
                ", lesserBoundaryCornerX=" + lesserBoundaryCornerX +
                ", lesserBoundaryCornerZ=" + lesserBoundaryCornerZ +
                ", greaterBoundaryCornerX=" + greaterBoundaryCornerX +
                ", greaterBoundaryCornerZ=" + greaterBoundaryCornerZ +
                ", config=" + config +
                ", users=" + users +
                ", id=" + getId() +
                '}';
    }
}
