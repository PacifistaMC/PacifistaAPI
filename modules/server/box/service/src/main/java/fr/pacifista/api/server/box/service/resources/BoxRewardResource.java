package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.box.client.clients.BoxRewardClient;
import fr.pacifista.api.server.box.client.clients.BoxRewardImplClient;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;
import fr.pacifista.api.server.box.service.services.BoxRewardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BoxRewardImplClient.PATH)
public class BoxRewardResource extends ApiResource<BoxRewardDTO, BoxRewardService> implements BoxRewardClient {

    public BoxRewardResource(BoxRewardService boxRewardService) {
        super(boxRewardService);
    }

}
