package fr.pacifista.api.client.server.players.players_sync.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerExperienceDataDTO extends ApiDTO {
    @NotNull
    private UUID playerOwnerUuid;

    @NotNull
    private ServerGameMode gameMode;

    @NotNull
    private Integer totalExperience;

    @NotNull
    private Float exp;

    @NotNull
    private Integer level;
}
