package fr.pacifista.api.server.players.sync.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.sync.client.clients.PlayerEnderchestDataClient;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.server.players.sync.service.services.PlayerEnderchestDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/enderchests")
public class PlayerEnderchestDataResource extends ApiResource<PlayerEnderchestDataDTO, PlayerEnderchestDataService> implements PlayerEnderchestDataClient {
    public PlayerEnderchestDataResource(PlayerEnderchestDataService playerEnderchestDataService) {
        super(playerEnderchestDataService);
    }
}
