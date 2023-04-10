package fr.pacifista.api.service.players.players_sync.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.players.players_sync.entities.PlayerExperienceData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerExperienceDataRepository extends ApiRepository<PlayerExperienceData> {
}
