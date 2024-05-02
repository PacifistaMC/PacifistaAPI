package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PacifistaWebUserLinkClientImpl.PATH)
public class PacifistaWebUserLinkResource extends ApiResource<PacifistaWebUserLinkDTO, PacifistaWebUserLinkService> {

    public PacifistaWebUserLinkResource(PacifistaWebUserLinkService service) {
        super(service);
    }

}
