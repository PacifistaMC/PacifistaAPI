package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.box.client.clients.BoxClient;
import fr.pacifista.api.server.box.client.clients.BoxImplClient;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.service.services.BoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BoxImplClient.PATH)
public class BoxResource extends ApiResource<BoxDTO, BoxService> implements BoxClient {

    public BoxResource(BoxService boxService) {
        super(boxService);
    }

}
