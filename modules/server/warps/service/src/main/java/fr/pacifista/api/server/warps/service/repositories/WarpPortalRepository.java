package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPortal;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpPortalRepository extends ApiRepository<WarpPortal> {
    void deleteAllByWarpIn(Iterable<Warp> warps);
}
