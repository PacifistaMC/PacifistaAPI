package fr.pacifista.api.service.server.warps.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.warps.entities.Warp;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpRepository extends ApiRepository<Warp> {
}
