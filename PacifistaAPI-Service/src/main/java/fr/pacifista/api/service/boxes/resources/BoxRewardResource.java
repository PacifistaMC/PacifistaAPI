package fr.pacifista.api.service.boxes.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.boxes.services.BoxRewardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box/rewards")
public class BoxRewardResource extends ApiResource<BoxRewardDTO, BoxRewardService> {

    public BoxRewardResource(BoxRewardService boxRewardService) {
        super(boxRewardService);
    }

}
