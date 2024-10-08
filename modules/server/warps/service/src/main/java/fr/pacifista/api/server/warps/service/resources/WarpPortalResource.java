package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.warps.client.clients.WarpClientImpl;
import fr.pacifista.api.server.warps.client.clients.WarpPortalClient;
import fr.pacifista.api.server.warps.client.clients.WarpPortalClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import fr.pacifista.api.server.warps.service.services.WarpPortalCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WarpPortalClientImpl.PATH)
public class WarpPortalResource extends ApiResource<WarpPortalDTO, WarpPortalCrudService> implements WarpPortalClient {
    public WarpPortalResource(WarpPortalCrudService service) {
        super(service);
    }
}
