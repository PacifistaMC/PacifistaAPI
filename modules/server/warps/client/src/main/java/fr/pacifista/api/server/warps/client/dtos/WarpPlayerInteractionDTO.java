package fr.pacifista.api.server.warps.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.server.warps.client.enums.WarpInteractionType;
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
public class WarpPlayerInteractionDTO extends ApiDTO {

    /**
     * Le warp avec lequel le joueur a interagi
     */
    @NotNull
    private WarpDTO warp;

    /**
     * UUID du joueur qui a interagi avec le warp
     */
    @NotNull
    private UUID playerId;

    /**
     * Type d'interaction
     */
    @NotNull
    private WarpInteractionType interactionType;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final WarpPlayerInteractionDTO other) {
            return playerId.equals(other.playerId) &&
                    warp.getId().equals(other.warp.getId()) &&
                    interactionType.equals(other.interactionType) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return playerId.hashCode() +
                warp.getId().hashCode() +
                interactionType.hashCode() +
                super.hashCode();
    }
}
