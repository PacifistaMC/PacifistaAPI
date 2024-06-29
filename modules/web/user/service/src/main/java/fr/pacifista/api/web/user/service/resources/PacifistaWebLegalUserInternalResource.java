package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebLegalUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kubeinternal/web/user/legal-document-user")
public class PacifistaWebLegalUserInternalResource extends ApiResource<PacifistaWebLegalUserDTO, PacifistaWebLegalUserService> {

    public PacifistaWebLegalUserInternalResource(PacifistaWebLegalUserService service) {
        super(service);
    }

}