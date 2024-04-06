package fr.pacifista.api.server.players.sync.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.players.sync.service.entities.PlayerInventoryData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerInventoryDataRepository extends ApiRepository<PlayerInventoryData> {
    Optional<PlayerInventoryData> findByPlayerOwnerUuidAndGameMode(String playerOwnerUuid, ServerGameMode gameMode);
}
