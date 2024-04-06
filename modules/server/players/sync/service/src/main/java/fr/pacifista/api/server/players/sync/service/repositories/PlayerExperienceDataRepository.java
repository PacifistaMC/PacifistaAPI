package fr.pacifista.api.server.players.sync.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.players.sync.service.entities.PlayerExperienceData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerExperienceDataRepository extends ApiRepository<PlayerExperienceData> {
    Optional<PlayerExperienceData> findByPlayerOwnerUuidAndGameMode(String playerOwnerUuid, ServerGameMode gameMode);
}
