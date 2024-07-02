package fr.pacifista.api.server.essentials.client.ingore_players.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerIgnoreDTO extends ApiDTO {

    @NotNull(message = "Player UUID is required")
    private UUID playerId;

    @NotNull(message = "Ignored player UUID is required")
    private UUID ignoredPlayerId;

}
