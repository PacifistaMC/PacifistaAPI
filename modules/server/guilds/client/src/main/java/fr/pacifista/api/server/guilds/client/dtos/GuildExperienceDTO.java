package fr.pacifista.api.server.guilds.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GuildExperienceDTO extends ApiDTO {
    @NotNull
    private UUID guildId;

    @NotNull
    private Integer level;

    @NotNull
    private Integer experience;
}
