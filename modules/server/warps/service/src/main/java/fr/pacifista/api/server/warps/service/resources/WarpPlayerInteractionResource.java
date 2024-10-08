package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.warps.client.clients.WarpPlayerInteractionClient;
import fr.pacifista.api.server.warps.client.clients.WarpPlayerInteractionClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.service.services.WarpPlayerInteractionCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WarpPlayerInteractionClientImpl.PATH)
public class WarpPlayerInteractionResource extends ApiResource<WarpPlayerInteractionDTO, WarpPlayerInteractionCrudService> implements WarpPlayerInteractionClient {

    public WarpPlayerInteractionResource(WarpPlayerInteractionCrudService service) {
        super(service);
    }

}
