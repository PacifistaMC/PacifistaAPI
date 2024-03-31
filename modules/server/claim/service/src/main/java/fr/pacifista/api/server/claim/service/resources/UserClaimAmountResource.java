package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.claim.client.clients.UserClaimAmountClientImpl;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;
import fr.pacifista.api.server.claim.service.services.UserClaimAmountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + UserClaimAmountClientImpl.PATH)
public class UserClaimAmountResource extends ApiResource<UserClaimAmountDTO, UserClaimAmountService> {

    public UserClaimAmountResource(UserClaimAmountService userClaimAmountService) {
        super(userClaimAmountService);
    }

}
