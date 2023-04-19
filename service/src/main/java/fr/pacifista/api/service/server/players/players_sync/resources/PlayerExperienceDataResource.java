package fr.pacifista.api.service.server.players.players_sync.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.players.players_sync.clients.PlayerExperienceDataClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.service.server.players.players_sync.services.PlayerExperienceDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/experience")
public class PlayerExperienceDataResource extends ApiResource<PlayerExperienceDataDTO, PlayerExperienceDataService> implements PlayerExperienceDataClient {
    public PlayerExperienceDataResource(PlayerExperienceDataService playerExperienceDataService) {
        super(playerExperienceDataService);
    }
}
