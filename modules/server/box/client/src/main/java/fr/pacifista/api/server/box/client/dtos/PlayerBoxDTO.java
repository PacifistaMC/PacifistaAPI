package fr.pacifista.api.server.box.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
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
