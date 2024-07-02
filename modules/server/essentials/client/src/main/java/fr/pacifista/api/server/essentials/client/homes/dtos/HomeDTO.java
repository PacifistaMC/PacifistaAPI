package fr.pacifista.api.server.essentials.client.homes.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * Material du home qui le définit, peut être utilisé pour le visuel dans des menus ou autre
     */
    @Nullable
    private String material;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final HomeDTO homeDTO) {
            return super.equals(obj) &&
                    homeDTO.getPlayerUuid().equals(this.playerUuid) &&
                    homeDTO.getName().equals(this.name) &&
                    (homeDTO.getMaterial() == null ? this.material == null : homeDTO.getMaterial().equals(this.material));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                this.playerUuid.hashCode() +
                this.name.hashCode() +
                (this.material == null ? 0 : this.material.hashCode());
    }
}
