package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.box.client.clients.PlayerBoxClient;
import fr.pacifista.api.server.box.client.clients.PlayerBoxImplClient;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
import fr.pacifista.api.server.box.service.services.PlayerBoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PlayerBoxImplClient.PATH)
public class PlayerBoxResource extends ApiResource<PlayerBoxDTO, PlayerBoxService> implements PlayerBoxClient {
    public PlayerBoxResource(PlayerBoxService playerBoxService) {
        super(playerBoxService);
    }
}
