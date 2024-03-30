package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.claim.client.clients.ClaimDataConfigClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.service.services.ClaimDataConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + ClaimDataConfigClientImpl.PATH)
public class ClaimDataConfigResource extends ApiResource<ClaimDataConfigDTO, ClaimDataConfigService> {
    public ClaimDataConfigResource(ClaimDataConfigService claimDataConfigService) {
        super(claimDataConfigService);
    }
}
