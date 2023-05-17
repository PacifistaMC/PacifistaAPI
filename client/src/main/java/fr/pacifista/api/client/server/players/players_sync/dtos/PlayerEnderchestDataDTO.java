package fr.pacifista.api.client.server.players.players_sync.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
