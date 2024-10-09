package fr.pacifista.api.server.warps.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WarpConfigRepository extends ApiRepository<WarpConfig> {
    void deleteAllByWarpIn(Iterable<Warp> warps);
    Set<WarpConfig> findAllByWarpIn(@NonNull Iterable<Warp> entity);
}
