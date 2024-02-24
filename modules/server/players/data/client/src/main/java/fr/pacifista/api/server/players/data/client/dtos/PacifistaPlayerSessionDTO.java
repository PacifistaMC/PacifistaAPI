package fr.pacifista.api.server.players.data.client.dtos;

import fr.pacifista.api.core.client.dtos.MinecraftPlayerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PacifistaPlayerSessionDTO extends MinecraftPlayerDTO {

    /**
     * Date of connection.
     */
    @NotNull
    private Date connectedAt;

    /**
     * Date of disconnection.
     */
    @NotNull
    private Date disconnectedAt;

}
