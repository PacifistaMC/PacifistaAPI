package fr.pacifista.api.server.players.sync.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.players.sync.service.entities.PlayerMoneyData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMoneyDataRepository extends ApiRepository<PlayerMoneyData> {
}
