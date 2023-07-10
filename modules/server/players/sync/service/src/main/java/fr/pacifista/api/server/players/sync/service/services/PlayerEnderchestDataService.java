package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerEnderchestData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerEnderchestDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerEnderchestDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerEnderchestDataService extends ApiService<PlayerEnderchestDataDTO, PlayerEnderchestData, PlayerEnderchestDataMapper, PlayerEnderchestDataRepository> {

    public PlayerEnderchestDataService(PlayerEnderchestDataRepository repository,
                                       PlayerEnderchestDataMapper mapper) {
        super(repository, mapper);
    }

}
