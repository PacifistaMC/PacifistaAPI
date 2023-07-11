package fr.pacifista.api.server.players.sync.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.players.sync.service.entities.PlayerInventoryData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInventoryDataRepository extends ApiRepository<PlayerInventoryData> {
}
