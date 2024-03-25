package fr.pacifista.api.server.essentials.client.homes.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HomeDTO extends LocationDTO {

    /**
     * UUID du joueur minecraft
     */
    @NotNull(message = "playerUuid est requis")
    private UUID playerUuid;

    /**
     * Nom du home
     */
    @NotNull(message = "Le nom du home est requis")
    private String name;

}
