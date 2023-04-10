package fr.pacifista.api.client.boxes.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerBoxDTO extends ApiDTO {
    @NotNull
    private UUID playerUuid;

    @NotNull
    private BoxDTO box;

    @NotNull
    private Integer amount;
}
