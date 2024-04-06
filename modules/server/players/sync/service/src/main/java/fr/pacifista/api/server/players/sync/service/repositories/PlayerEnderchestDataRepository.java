package fr.pacifista.api.server.players.sync.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.players.sync.service.entities.PlayerEnderchestData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerEnderchestDataRepository extends ApiRepository<PlayerEnderchestData> {
    Optional<PlayerEnderchestDataRepository> findByPlayerOwnerUuidAndGameMode(String playerOwnerUuid, ServerGameMode gameMode);
}
