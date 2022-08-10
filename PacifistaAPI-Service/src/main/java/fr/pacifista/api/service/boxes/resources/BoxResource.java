package fr.pacifista.api.service.boxes.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.boxes.services.BoxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box")
public class BoxResource extends ApiResource<BoxDTO, BoxService> {

    public BoxResource(BoxService boxService) {
        super(boxService);
    }

}
