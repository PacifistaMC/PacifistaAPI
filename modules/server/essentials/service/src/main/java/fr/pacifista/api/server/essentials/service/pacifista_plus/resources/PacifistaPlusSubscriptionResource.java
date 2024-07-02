package fr.pacifista.api.server.essentials.service.pacifista_plus.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.pacifista_plus.clients.PacifistaPlusSubscriptionClientImpl;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import fr.pacifista.api.server.essentials.service.pacifista_plus.services.PacifistaPlusSubscriptionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PacifistaPlusSubscriptionClientImpl.PATH)
public class PacifistaPlusSubscriptionResource extends ApiResource<PacifistaPlusSubscriptionDTO, PacifistaPlusSubscriptionService> {

    public PacifistaPlusSubscriptionResource(PacifistaPlusSubscriptionService service) {
        super(service);
    }

}
