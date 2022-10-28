package fr.pacifista.api.client.guilds.dtos;

import fr.pacifista.api.client.core.dtos.LocationDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class GuildHomeDTO extends LocationDTO {
    @NotNull
    private UUID guildId;

    @NotNull
    private Boolean publicAccess;
}
