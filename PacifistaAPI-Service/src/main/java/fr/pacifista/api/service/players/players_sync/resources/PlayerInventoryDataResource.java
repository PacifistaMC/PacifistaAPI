package fr.pacifista.api.service.players.players_sync.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.players.players_sync.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.service.players.players_sync.services.PlayerInventoryDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/inventory")
public class PlayerInventoryDataResource extends ApiResource<PlayerInventoryDataDTO, PlayerInventoryDataService> {
    public PlayerInventoryDataResource(PlayerInventoryDataService playerInventoryDataService) {
        super(playerInventoryDataService);
    }
}
