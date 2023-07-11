package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.service.entities.Warp;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpRepository extends ApiRepository<Warp> {
}
