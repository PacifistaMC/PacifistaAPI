package fr.pacifista.api.server.essentials.service.holograms.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.holograms.entities.Hologram;
import org.springframework.stereotype.Repository;

@Repository
public interface HologramRepository extends ApiRepository<Hologram> {
}
