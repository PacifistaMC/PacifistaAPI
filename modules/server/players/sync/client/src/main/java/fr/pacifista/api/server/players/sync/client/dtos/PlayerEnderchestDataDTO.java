package fr.pacifista.api.server.players.sync.client.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerEnderchestDataDTO extends PlayerDataServerDTO {
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
