package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerMoneyData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerMoneyDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerMoneyDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerMoneyDataService extends ApiService<PlayerMoneyDataDTO, PlayerMoneyData, PlayerMoneyDataMapper, PlayerMoneyDataRepository> {
    public PlayerMoneyDataService(PlayerMoneyDataRepository repository,
                                  PlayerMoneyDataMapper mapper) {
        super(repository, mapper);
    }
}
