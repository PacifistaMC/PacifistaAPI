package fr.pacifista.api.server.essentials.service.ignore_players.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.ingore_players.clients.PlayerIgnoreClientImpl;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;
import fr.pacifista.api.server.essentials.service.ignore_players.services.PlayerIgnoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PlayerIgnoreClientImpl.PATH)
public class PlayerIgnoreResource extends ApiResource<PlayerIgnoreDTO, PlayerIgnoreService> {

    public PlayerIgnoreResource(PlayerIgnoreService service) {
        super(service);
    }

}
