package fr.pacifista.api.server.essentials.service.holograms.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.holograms.clients.HologramClient;
import fr.pacifista.api.server.essentials.client.holograms.clients.HologramClientImpl;
import fr.pacifista.api.server.essentials.client.holograms.dtos.HologramDTO;
import fr.pacifista.api.server.essentials.service.holograms.services.HologramService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + HologramClientImpl.PATH)
public class HologramResource extends ApiResource<HologramDTO, HologramService> implements HologramClient {

    public HologramResource(HologramService hologramService) {
        super(hologramService);
    }

}
