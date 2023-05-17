package fr.pacifista.api.service.server.boxes.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.boxes.clients.PlayerBoxClient;
import fr.pacifista.api.client.server.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.server.boxes.services.PlayerBoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box/player")
public class PlayerBoxResource extends ApiResource<PlayerBoxDTO, PlayerBoxService> implements PlayerBoxClient {
    public PlayerBoxResource(PlayerBoxService playerBoxService) {
        super(playerBoxService);
    }
}
