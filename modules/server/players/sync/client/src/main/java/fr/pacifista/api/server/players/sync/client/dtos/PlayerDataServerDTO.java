package fr.pacifista.api.server.players.sync.client.dtos;

import fr.pacifista.api.core.client.enums.ServerGameMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PlayerDataServerDTO extends PlayerDataDTO {
    @NotNull
    private ServerGameMode gameMode;
}
