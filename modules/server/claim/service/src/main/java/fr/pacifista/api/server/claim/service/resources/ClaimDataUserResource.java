package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.claim.client.clients.ClaimDataUserClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import fr.pacifista.api.server.claim.service.services.ClaimDataUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + ClaimDataUserClientImpl.PATH)
public class ClaimDataUserResource extends ApiResource<ClaimDataUserDTO, ClaimDataUserService> {
    public ClaimDataUserResource(ClaimDataUserService claimDataUserService) {
        super(claimDataUserService);
    }
}
