package fr.pacifista.api.client.server.guilds.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GuildLogDTO extends ApiDTO {
    @NotNull
    private UUID guildId;

    @NotNull
    private UUID playerUuid;

    @NotBlank
    private String action;
}
