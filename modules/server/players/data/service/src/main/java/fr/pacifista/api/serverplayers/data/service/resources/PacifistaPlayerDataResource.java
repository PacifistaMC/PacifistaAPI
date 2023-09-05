package fr.pacifista.api.serverplayers.data.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.serverplayers.data.client.clients.PacifistaPlayerDataClient;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.serverplayers.data.service.services.PacifistaPlayerDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playerdata/data")
public class PacifistaPlayerDataResource extends ApiResource<PacifistaPlayerDataDTO, PacifistaPlayerDataService> implements PacifistaPlayerDataClient {

    public PacifistaPlayerDataResource(PacifistaPlayerDataService pacifistaPlayerDataService) {
        super(pacifistaPlayerDataService);
    }

}
