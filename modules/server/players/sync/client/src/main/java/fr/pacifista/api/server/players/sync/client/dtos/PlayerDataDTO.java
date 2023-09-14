package fr.pacifista.api.server.players.sync.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class PlayerDataDTO extends ApiDTO {
    @NotNull
    private UUID playerOwnerUuid;
}
