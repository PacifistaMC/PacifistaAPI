package fr.pacifista.api.service.players.players_sync.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.players.players_sync.entities.PlayerInventoryData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInventoryDataRepository extends ApiRepository<PlayerInventoryData> {
}
