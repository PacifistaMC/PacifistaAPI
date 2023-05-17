package fr.pacifista.api.client.server.guilds.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.server.guilds.enums.GuildRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GuildMemberDTO extends ApiDTO {
    @NotNull
    private UUID guildId;

    @NotNull
    private UUID playerUuid;

    @NotNull
    private GuildRole role;
}
