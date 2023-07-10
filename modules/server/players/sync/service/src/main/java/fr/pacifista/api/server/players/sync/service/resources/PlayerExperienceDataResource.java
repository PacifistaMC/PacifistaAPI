package fr.pacifista.api.server.players.sync.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.sync.client.clients.PlayerExperienceDataClient;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.server.players.sync.service.services.PlayerExperienceDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playersync/experience")
public class PlayerExperienceDataResource extends ApiResource<PlayerExperienceDataDTO, PlayerExperienceDataService> implements PlayerExperienceDataClient {
    public PlayerExperienceDataResource(PlayerExperienceDataService playerExperienceDataService) {
        super(playerExperienceDataService);
    }
}
