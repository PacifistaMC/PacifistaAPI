package fr.pacifista.api.client.guilds.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
