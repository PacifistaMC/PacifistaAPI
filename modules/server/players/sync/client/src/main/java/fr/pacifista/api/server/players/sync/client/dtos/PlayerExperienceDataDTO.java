package fr.pacifista.api.server.players.sync.client.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerExperienceDataDTO extends PlayerDataServerDTO {
    @NotNull
    private Integer totalExperience;

    @NotNull
    private Float exp;

    @NotNull
    private Integer level;
}
