package fr.pacifista.api.client.core.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public abstract class LocationDTO extends ApiDTO {
    @NotNull
    private UUID worldUuid;

    @NotNull
    private ServerType serverType;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    @NotNull
    private Double z;

    @NotNull
    private Float yaw;

    @NotNull
    private Float pitch;
}
