package fr.pacifista.api.service.server.warps.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.warps.clients.WarpClient;
import fr.pacifista.api.client.server.warps.dtos.WarpDTO;
import fr.pacifista.api.service.server.warps.services.WarpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warps")
public class WarpResource extends ApiResource<WarpDTO, WarpService> implements WarpClient {
    public WarpResource(WarpService service) {
        super(service);
    }
}
