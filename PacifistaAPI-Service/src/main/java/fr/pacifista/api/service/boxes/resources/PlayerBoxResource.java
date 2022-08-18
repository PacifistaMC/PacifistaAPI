package fr.pacifista.api.service.boxes.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.boxes.clients.PlayerBoxClient;
import fr.pacifista.api.client.modules.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.boxes.services.PlayerBoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box/player")
public class PlayerBoxResource extends ApiResource<PlayerBoxDTO, PlayerBoxService> implements PlayerBoxClient {
    public PlayerBoxResource(PlayerBoxService playerBoxService) {
        super(playerBoxService);
    }
}
