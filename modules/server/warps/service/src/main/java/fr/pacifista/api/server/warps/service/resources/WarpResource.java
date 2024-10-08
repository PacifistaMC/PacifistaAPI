package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.warps.client.clients.WarpClient;
import fr.pacifista.api.server.warps.client.clients.WarpClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.services.WarpConfigCrudService;
import fr.pacifista.api.server.warps.service.services.WarpCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WarpClientImpl.PATH)
public class WarpResource extends ApiResource<WarpDTO, WarpCrudService> implements WarpClient {

    private final WarpConfigCrudService warpConfigCrudService;

    public WarpResource(WarpCrudService service,
                        WarpConfigCrudService warpConfigCrudService) {
        super(service);
        this.warpConfigCrudService = warpConfigCrudService;
    }

    @Override
    public WarpConfigDTO updateConfig(WarpConfigDTO warpConfigDTO) {
        return this.warpConfigCrudService.updatePut(warpConfigDTO);
    }
}
