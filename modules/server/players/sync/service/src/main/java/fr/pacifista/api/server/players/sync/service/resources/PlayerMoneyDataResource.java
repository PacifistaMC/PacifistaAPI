package fr.pacifista.api.server.players.sync.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.sync.client.clients.PlayerMoneyDataClient;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.server.players.sync.service.services.PlayerMoneyDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/money")
public class PlayerMoneyDataResource extends ApiResource<PlayerMoneyDataDTO, PlayerMoneyDataService> implements PlayerMoneyDataClient {
    public PlayerMoneyDataResource(PlayerMoneyDataService playerMoneyDataService) {
        super(playerMoneyDataService);
    }
}
