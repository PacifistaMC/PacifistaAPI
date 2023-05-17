package fr.pacifista.api.service.server.players.players_sync.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerExperienceData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerExperienceDataRepository extends ApiRepository<PlayerExperienceData> {
}
