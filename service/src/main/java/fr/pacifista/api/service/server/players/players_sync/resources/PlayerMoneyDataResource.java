package fr.pacifista.api.service.server.players.players_sync.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.players.players_sync.clients.PlayerMoneyDataClient;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.service.server.players.players_sync.services.PlayerMoneyDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/money")
public class PlayerMoneyDataResource extends ApiResource<PlayerMoneyDataDTO, PlayerMoneyDataService> implements PlayerMoneyDataClient {
    public PlayerMoneyDataResource(PlayerMoneyDataService playerMoneyDataService) {
        super(playerMoneyDataService);
    }
}
