package fr.pacifista.api.client.server.players.players_sync.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerMoneyDataDTO extends ApiDTO {
    @NotNull
    private UUID playerOwnerUuid;

    @NotNull
    private Double money;
}
