package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebLegalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/user/legal-document")
public class PacifistaWebLegalResource extends ApiResource<PacifistaWebLegalDTO, PacifistaWebLegalService> {

    public PacifistaWebLegalResource(PacifistaWebLegalService service) {
        super(service);
    }

}
