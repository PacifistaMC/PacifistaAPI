package fr.pacifista.api.server.guilds.client.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
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
