package fr.pacifista.api.service.boxes.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.boxes.clients.PlayerBoxClient;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.boxes.services.PlayerBoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box/player")
public class PlayerBoxResource extends ApiResource<PlayerBoxDTO, PlayerBoxService> implements PlayerBoxClient {
    public PlayerBoxResource(PlayerBoxService playerBoxService) {
        super(playerBoxService);
    }

    @Override
    public void give(PlayerBoxDTO request) {
        getService().give(request);
    }

    @Override
    public void take(PlayerBoxDTO request) {
        getService().take(request);
    }
}
