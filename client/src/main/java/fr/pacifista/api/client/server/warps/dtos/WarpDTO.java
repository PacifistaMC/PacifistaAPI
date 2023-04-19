package fr.pacifista.api.client.warps.dtos;

import fr.pacifista.api.client.core.dtos.LocationDTO;
import fr.pacifista.api.client.warps.enums.WarpType;
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
