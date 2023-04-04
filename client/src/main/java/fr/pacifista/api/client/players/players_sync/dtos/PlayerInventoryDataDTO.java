package fr.pacifista.api.client.players.players_sync.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerInventoryDataDTO extends ApiDTO {
    @NotNull
    private UUID playerOwnerUuid;

    @NotNull
    private ServerGameMode gameMode;

    @NotBlank
    private String inventory;

    @NotBlank
    private String armor;
}
