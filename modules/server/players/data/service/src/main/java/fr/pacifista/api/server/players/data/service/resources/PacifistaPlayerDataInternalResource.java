package fr.pacifista.api.server.players.data.service.resources;

import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataImplClient;
import fr.pacifista.api.server.players.data.service.services.PacifistaPlayerDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PacifistaPlayerDataImplClient.INTERNAL_PATH)
public class PacifistaPlayerDataInternalResource extends PacifistaPlayerDataResource {

    public PacifistaPlayerDataInternalResource(PacifistaPlayerDataService pacifistaPlayerDataService) {
        super(pacifistaPlayerDataService);
    }

}
