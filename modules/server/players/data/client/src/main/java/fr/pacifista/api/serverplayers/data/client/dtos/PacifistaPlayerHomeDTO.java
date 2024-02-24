package fr.pacifista.api.serverplayers.data.client.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PacifistaPlayerHomeDTO extends LocationDTO {

    /**
     * The player's minecraft uuid
     */
    private UUID minecraftUuid;

    /**
     * The player's home name
     */
    private String name;

    /**
     * The player's home server type
     */
    private ServerType serverType;

}
