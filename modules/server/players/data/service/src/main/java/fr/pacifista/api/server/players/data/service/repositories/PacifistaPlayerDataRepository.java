package fr.pacifista.api.server.players.data.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerData;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPlayerDataRepository extends ApiRepository<PacifistaPlayerData> {
}
