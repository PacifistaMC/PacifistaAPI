package fr.pacifista.api.service.server.players.players_sync.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerEnderchestData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerEnderchestDataRepository extends ApiRepository<PlayerEnderchestData> {
}
