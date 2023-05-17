package fr.pacifista.api.service.server.players.players_sync.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerMoneyData;
import fr.pacifista.api.service.server.players.players_sync.mappers.PlayerMoneyDataMapper;
import fr.pacifista.api.service.server.players.players_sync.repositories.PlayerMoneyDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerMoneyDataService extends ApiService<PlayerMoneyDataDTO, PlayerMoneyData, PlayerMoneyDataMapper, PlayerMoneyDataRepository> {
    public PlayerMoneyDataService(PlayerMoneyDataRepository repository,
                                  PlayerMoneyDataMapper mapper) {
        super(repository, mapper);
    }
}
