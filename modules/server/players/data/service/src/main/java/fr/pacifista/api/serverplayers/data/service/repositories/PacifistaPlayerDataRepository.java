package fr.pacifista.api.serverplayers.data.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.serverplayers.data.service.entities.PacifistaPlayerData;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPlayerDataRepository extends ApiRepository<PacifistaPlayerData> {
}
