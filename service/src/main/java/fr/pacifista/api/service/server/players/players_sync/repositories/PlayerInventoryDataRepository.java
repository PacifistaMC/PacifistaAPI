package fr.pacifista.api.service.server.players.players_sync.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerInventoryData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInventoryDataRepository extends ApiRepository<PlayerInventoryData> {
}
