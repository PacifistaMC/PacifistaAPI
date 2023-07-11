package fr.pacifista.api.server.warps.client.dtos;

import fr.pacifista.api.core.client.enums.dtos.LocationDTO;
import fr.pacifista.api.server.warps.client.enums.WarpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WarpDTO extends LocationDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String warpItem;

    @NotNull
    private Boolean publicAccess = true;

    @NotNull
    private UUID playerOwnerUuid;

    @NotNull
    private WarpType type;
}
