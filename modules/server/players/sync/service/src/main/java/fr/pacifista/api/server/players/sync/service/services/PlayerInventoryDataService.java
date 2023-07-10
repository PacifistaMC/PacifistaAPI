package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerInventoryData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerInventoryDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerInventoryDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerInventoryDataService extends ApiService<PlayerInventoryDataDTO, PlayerInventoryData, PlayerInventoryDataMapper, PlayerInventoryDataRepository> {
    public PlayerInventoryDataService(PlayerInventoryDataRepository repository, PlayerInventoryDataMapper mapper) {
        super(repository, mapper);
    }
}
