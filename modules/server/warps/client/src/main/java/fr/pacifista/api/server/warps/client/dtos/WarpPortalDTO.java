package fr.pacifista.api.server.warps.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarpPortalDTO extends ApiDTO {

    /**
     * La destination du portail de tp
     */
    @NotBlank
    private WarpDTO destinationWarp;

    /**
     * Type de serveur
     */
    @NotNull(message = "serverType is required")
    private ServerType serverType;

    /**
     * uid du monde
     */
    @NotNull(message = "worldId is required")
    private UUID worldId;

    /**
     * Bloc dans le coin à la position basse x
     */
    @NotNull(message = "lesserBoundaryCornerX is required")
    private Integer lesserBoundaryCornerX;

    /**
     * Bloc dans le coin à la position basse y
     */
    @NotNull(message = "lesserBoundaryCornerY is required")
    private Integer lesserBoundaryCornerY;

    /**
     * Bloc dans le coin à la position basse z
     */
    @NotNull(message = "lesserBoundaryCornerZ is required")
    private Integer lesserBoundaryCornerZ;

    /**
     * Bloc dans le coin à la position haute x
     */
    @NotNull(message = "greaterBoundaryCornerX is required")
    private Integer greaterBoundaryCornerX;

    /**
     * Bloc dans le coin à la position haute y
     */
    @NotNull(message = "greaterBoundaryCornerY is required")
    private Integer greaterBoundaryCornerY;

    /**
     * Bloc dans le coin à la position haute z
     */
    @NotNull(message = "greaterBoundaryCornerZ is required")
    private Integer greaterBoundaryCornerZ;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final WarpPortalDTO warpPortalDTO) {
            return this.destinationWarp.getId().equals(warpPortalDTO.destinationWarp.getId()) &&
                    this.serverType.equals(warpPortalDTO.serverType) &&
                    this.worldId.equals(warpPortalDTO.worldId) &&
                    this.lesserBoundaryCornerX.equals(warpPortalDTO.lesserBoundaryCornerX) &&
                    this.lesserBoundaryCornerY.equals(warpPortalDTO.lesserBoundaryCornerY) &&
                    this.lesserBoundaryCornerZ.equals(warpPortalDTO.lesserBoundaryCornerZ) &&
                    this.greaterBoundaryCornerX.equals(warpPortalDTO.greaterBoundaryCornerX) &&
                    this.greaterBoundaryCornerY.equals(warpPortalDTO.greaterBoundaryCornerY) &&
                    this.greaterBoundaryCornerZ.equals(warpPortalDTO.greaterBoundaryCornerZ) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.destinationWarp.getId().hashCode() +
                this.serverType.hashCode() +
                this.worldId.hashCode() +
                this.lesserBoundaryCornerX.hashCode() +
                this.lesserBoundaryCornerY.hashCode() +
                this.lesserBoundaryCornerZ.hashCode() +
                this.greaterBoundaryCornerX.hashCode() +
                this.greaterBoundaryCornerY.hashCode() +
                this.greaterBoundaryCornerZ.hashCode() +
                super.hashCode();
    }
}
