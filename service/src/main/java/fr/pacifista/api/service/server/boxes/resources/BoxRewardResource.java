package fr.pacifista.api.service.server.boxes.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.boxes.clients.BoxRewardClient;
import fr.pacifista.api.client.server.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.server.boxes.services.BoxRewardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("box/rewards")
public class BoxRewardResource extends ApiResource<BoxRewardDTO, BoxRewardService> implements BoxRewardClient {

    public BoxRewardResource(BoxRewardService boxRewardService) {
        super(boxRewardService);
    }

}
