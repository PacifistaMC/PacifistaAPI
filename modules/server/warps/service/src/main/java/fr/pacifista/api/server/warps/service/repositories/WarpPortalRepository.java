package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPortal;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarpPortalRepository extends ApiRepository<WarpPortal> {
    Optional<WarpPortal> findByWarp(Warp warp);
}
