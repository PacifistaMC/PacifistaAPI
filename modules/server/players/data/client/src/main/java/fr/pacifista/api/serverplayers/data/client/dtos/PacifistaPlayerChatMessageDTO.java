package fr.pacifista.api.serverplayers.data.client.dtos;

import fr.pacifista.api.core.client.dtos.MinecraftPlayerDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaPlayerChatMessageDTO extends MinecraftPlayerDTO {

    @NotBlank
    private String message;

    @NotNull
    private ServerType serverType;

    @NotNull
    private Boolean isCommand;

}
