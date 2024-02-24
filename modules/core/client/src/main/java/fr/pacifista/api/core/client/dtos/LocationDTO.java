package fr.pacifista.api.core.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
