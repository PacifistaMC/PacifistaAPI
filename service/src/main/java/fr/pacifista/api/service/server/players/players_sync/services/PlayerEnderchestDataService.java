package fr.pacifista.api.service.server.players.players_sync.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerEnderchestData;
import fr.pacifista.api.service.server.players.players_sync.mappers.PlayerEnderchestDataMapper;
import fr.pacifista.api.service.server.players.players_sync.repositories.PlayerEnderchestDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerEnderchestDataService extends ApiService<PlayerEnderchestDataDTO, PlayerEnderchestData, PlayerEnderchestDataMapper, PlayerEnderchestDataRepository> {

    public PlayerEnderchestDataService(PlayerEnderchestDataRepository repository,
                                       PlayerEnderchestDataMapper mapper) {
        super(repository, mapper);
    }

}
