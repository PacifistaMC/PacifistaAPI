package fr.pacifista.api.client.players.players_sync.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
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
