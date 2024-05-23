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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final HomeDTO homeDTO) {
            return super.equals(obj) &&
                    homeDTO.getPlayerUuid().equals(this.playerUuid) &&
                    homeDTO.getName().equals(this.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.playerUuid.hashCode() + this.name.hashCode();
    }
}
