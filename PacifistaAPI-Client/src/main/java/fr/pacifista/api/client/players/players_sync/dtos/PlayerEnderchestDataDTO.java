package fr.pacifista.api.client.players.players_sync.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class PlayerEnderchestDataDTO extends ApiDTO {
    @NotNull
    private UUID playerOwnerUuid;

    @NotNull
    private ServerGameMode gameMode;

    @NotBlank
    private String enderchestSerialized;

    @NotBlank
    private String enderchestPaladinSerialized;

    @NotBlank
    private String enderchestEliteSerialized;

    @NotBlank
    private String enderchestLegendaireSerialized;

    @NotBlank
    private String enderchestMineSerialized;
}
