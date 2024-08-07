package fr.pacifista.api.server.essentials.client.holograms.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HologramDTO extends LocationDTO {

    /**
     * Le texte de l'hologramme sérialisé, en utilisant la lib Minecraft Adventure.
     */
    @NotBlank(message = "The text of the hologram is required")
    private String textSerialized;

    @Nullable
    private HologramDTO parentHologram;

    /**
     * Les hologrammes enfants de cet hologramme qui sont affichés en dessous.
     */
    private List<HologramDTO> childHolograms;

    /**
     * Création d'un holograme sans parent
     * @param textSerialized Le texte de l'hologramme sérialisé, en utilisant la lib Minecraft Adventure.
     * @param serverType Le type de serveur
     * @param worldId L'UUID du monde
     * @param x La coordonnée X
     * @param y La coordonnée Y
     * @param z La coordonnée Z
     */
    public HologramDTO(final String textSerialized,
                       final ServerType serverType,
                       final UUID worldId,
                       final double x,
                       final double y,
                       final double z) {
        super.setServerType(serverType);
        super.setWorldUuid(worldId);
        super.setX(x);
        super.setY(y);
        super.setZ(z);
        super.setYaw(0.0f);
        super.setPitch(0.0f);

        this.textSerialized = textSerialized;
    }

    /**
     * Création d'un holograme avec parent.
     * @param textSerialized Le texte de l'hologramme sérialisé, en utilisant la lib Minecraft Adventure.
     * @param parentHologram L'hologramme parent
     */
    public HologramDTO(final String textSerialized,
                       final @NonNull HologramDTO parentHologram) {
        if (parentHologram.getId() == null) {
            throw new IllegalArgumentException("The parent hologram must have an ID (created in the database api)");
        }

        super.setServerType(parentHologram.getServerType());
        super.setWorldUuid(parentHologram.getWorldUuid());
        super.setX(parentHologram.getX());
        super.setY(this.getChildYPos(parentHologram));
        super.setZ(parentHologram.getZ());
        super.setYaw(0.0f);
        super.setPitch(0.0f);

        this.textSerialized = textSerialized;
        this.parentHologram = parentHologram;
    }

    public List<HologramDTO> getChildHolograms() {
        if (this.childHolograms == null) {
            return List.of();
        } else {
            return childHolograms.stream()
                    .sorted(Comparator.comparing(HologramDTO::getCreatedAt).reversed())
                    .toList();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final HologramDTO other) {
            return textSerialized.equals(other.textSerialized) &&
                    ((parentHologram != null && parentHologram.equals(other.parentHologram)) || parentHologram == null && other.parentHologram == null) &&
                    childHolograms.equals(other.childHolograms) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return textSerialized.hashCode() +
                (parentHologram == null ? 0 : parentHologram.hashCode()) +
                childHolograms.hashCode() +
                super.hashCode();
    }

    private double getChildYPos(final HologramDTO parentHologram) {
        double parentY;

        try {
            parentY = parentHologram.getChildHolograms().getLast().getY();
        } catch (NoSuchElementException e) {
            parentY = parentHologram.getY();
        }

        return parentY - 0.5;
    }
}
