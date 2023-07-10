package fr.pacifista.api.server.players.sync.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.sync.client.clients.PlayerInventoryDataClient;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.server.players.sync.service.services.PlayerInventoryDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/inventory")
public class PlayerInventoryDataResource extends ApiResource<PlayerInventoryDataDTO, PlayerInventoryDataService> implements PlayerInventoryDataClient {
    public PlayerInventoryDataResource(PlayerInventoryDataService playerInventoryDataService) {
        super(playerInventoryDataService);
    }
}
