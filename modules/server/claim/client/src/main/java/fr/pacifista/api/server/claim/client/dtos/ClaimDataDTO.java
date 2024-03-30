package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDataDTO extends ApiDTO {

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
}
