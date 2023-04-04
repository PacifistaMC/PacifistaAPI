package fr.pacifista.api.service.players.players_sync.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.players.players_sync.entities.PlayerMoneyData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMoneyDataRepository extends ApiRepository<PlayerMoneyData> {
}
