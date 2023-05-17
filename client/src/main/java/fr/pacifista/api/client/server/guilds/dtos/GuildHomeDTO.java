package fr.pacifista.api.client.server.guilds.dtos;

import fr.pacifista.api.client.core.dtos.LocationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GuildHomeDTO extends LocationDTO {
    @NotNull
    private UUID guildId;

    @NotNull
    private Boolean publicAccess;
}
