package fr.pacifista.api.web.user.service.resources;

import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PacifistaWebUserLinkClientImpl.INTERNAL_PATH)
public class PacifistaWebUserLinkInternalResource extends PacifistaWebUserLinkResource {

    public PacifistaWebUserLinkInternalResource(PacifistaWebUserLinkService service) {
        super(service);
    }
}
