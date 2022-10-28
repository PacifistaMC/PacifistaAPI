package fr.pacifista.api.service.players.players_sync.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.service.players.players_sync.entities.PlayerInventoryData;
import fr.pacifista.api.service.players.players_sync.mappers.PlayerInventoryDataMapper;
import fr.pacifista.api.service.players.players_sync.repositories.PlayerInventoryDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerInventoryDataService extends ApiService<PlayerInventoryDataDTO, PlayerInventoryData, PlayerInventoryDataMapper, PlayerInventoryDataRepository> {
    public PlayerInventoryDataService(PlayerInventoryDataRepository repository, PlayerInventoryDataMapper mapper) {
        super(repository, mapper);
    }
}
