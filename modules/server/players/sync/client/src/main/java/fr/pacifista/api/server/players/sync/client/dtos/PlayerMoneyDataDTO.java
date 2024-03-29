package fr.pacifista.api.server.players.sync.client.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerMoneyDataDTO extends PlayerDataDTO {
    @NotNull
    private Double money;

    @NotNull
    private Double offlineMoney;
}
