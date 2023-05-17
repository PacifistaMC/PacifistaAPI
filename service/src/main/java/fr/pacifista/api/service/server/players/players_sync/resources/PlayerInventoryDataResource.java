package fr.pacifista.api.service.server.players.players_sync.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.players.players_sync.clients.PlayerInventoryDataClient;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.service.server.players.players_sync.services.PlayerInventoryDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/inventory")
public class PlayerInventoryDataResource extends ApiResource<PlayerInventoryDataDTO, PlayerInventoryDataService> implements PlayerInventoryDataClient {
    public PlayerInventoryDataResource(PlayerInventoryDataService playerInventoryDataService) {
        super(playerInventoryDataService);
    }
}
