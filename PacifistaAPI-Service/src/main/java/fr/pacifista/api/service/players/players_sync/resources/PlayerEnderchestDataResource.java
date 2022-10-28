package fr.pacifista.api.service.players.players_sync.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.players.players_sync.clients.PlayerEnderchestDataClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.service.players.players_sync.services.PlayerEnderchestDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/enderchests")
public class PlayerEnderchestDataResource extends ApiResource<PlayerEnderchestDataDTO, PlayerEnderchestDataService> implements PlayerEnderchestDataClient {
    public PlayerEnderchestDataResource(PlayerEnderchestDataService playerEnderchestDataService) {
        super(playerEnderchestDataService);
    }
}
