package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpPlayerInteractionRepository extends ApiRepository<WarpPlayerInteraction> {
    void deleteAllByWarpIn(Iterable<Warp> warps);
}
