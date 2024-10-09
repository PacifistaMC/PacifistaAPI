package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.client.enums.WarpInteractionType;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarpPlayerInteractionRepository extends ApiRepository<WarpPlayerInteraction> {
    void deleteAllByWarpIn(Iterable<Warp> warps);
    Optional<WarpPlayerInteraction> findByWarpAndPlayerIdAndInteractionType(Warp warp, String playerId, WarpInteractionType interactionType);
}
