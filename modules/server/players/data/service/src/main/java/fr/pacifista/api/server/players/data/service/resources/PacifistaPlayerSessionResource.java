package fr.pacifista.api.server.players.data.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerSessionClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerSessionDTO;
import fr.pacifista.api.server.players.data.service.services.PacifistaPlayerSessionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playerdata/session")
public class PacifistaPlayerSessionResource extends ApiResource<PacifistaPlayerSessionDTO, PacifistaPlayerSessionService> implements PacifistaPlayerSessionClient {

    public PacifistaPlayerSessionResource(PacifistaPlayerSessionService pacifistaPlayerSessionService) {
        super(pacifistaPlayerSessionService);
    }

}
