package fr.pacifista.api.server.warps.client.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.server.warps.client.enums.WarpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarpDTO extends LocationDTO {

    /**
     * Nom du warp
     */
    @NotBlank
    private String name;

    /**
     * Description du warp formatté en json pour la lib Adventure sur Paper
     */
    @NotBlank
    private String jsonFormattedDescription;

    /**
     * L'item qui est affiché dans les menus pour accéder au warp
     */
    @NotBlank
    private String warpItem;

    /**
     * Configuration du warp
     */
    private WarpConfigDTO config;

    /**
     * UUID du joueur qui a créé le warp
     */
    @NotNull
    private UUID playerOwnerUuid;

    /**
     * Type de warp
     */
    @NotNull
    private WarpType type;

    /**
     * Nombre d'utilisations du warp
     */
    @NotNull
    private Integer uses;

    /**
     * Nombre de likes du warp
     */
    @NotNull
    private Integer likes;

    public WarpDTO(@NotBlank final String name,
                   @NotBlank final String jsonFormattedDescription,
                   @NotBlank final String warpItem,
                   @NotNull final UUID playerOwnerUuid,
                   @NotNull final WarpType type) {
        this.name = name;
        this.jsonFormattedDescription = jsonFormattedDescription;
        this.warpItem = warpItem;
        this.playerOwnerUuid = playerOwnerUuid;
        this.type = type;
        this.uses = 0;
        this.likes = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final WarpDTO other) {
            return name.equals(other.name) &&
                    jsonFormattedDescription.equals(other.jsonFormattedDescription) &&
                    warpItem.equals(other.warpItem) &&
                    config.equals(other.config) &&
                    playerOwnerUuid.equals(other.playerOwnerUuid) &&
                    type.equals(other.type) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode() +
                jsonFormattedDescription.hashCode() +
                warpItem.hashCode() +
                config.hashCode() +
                playerOwnerUuid.hashCode() +
                type.hashCode() +
                super.hashCode();
    }
}
