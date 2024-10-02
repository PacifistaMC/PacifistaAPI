package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.claim.client.clients.ClaimPhantomPreventionBlockClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;
import fr.pacifista.api.server.claim.service.services.ClaimPhantomPreventionBlockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + ClaimPhantomPreventionBlockClientImpl.PATH)
public class ClaimPhantomPreventionBlockResource extends ApiResource<ClaimPhantomPreventionBlockDTO, ClaimPhantomPreventionBlockService> {

    public ClaimPhantomPreventionBlockResource(ClaimPhantomPreventionBlockService service) {
        super(service);
    }

}
