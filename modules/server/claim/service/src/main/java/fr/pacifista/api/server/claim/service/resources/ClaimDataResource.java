package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.claim.client.clients.ClaimDataClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.service.services.ClaimDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + ClaimDataClientImpl.PATH)
public class ClaimDataResource extends ApiResource<ClaimDataDTO, ClaimDataService> {
    public ClaimDataResource(ClaimDataService claimDataService) {
        super(claimDataService);
    }
}
