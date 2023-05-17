package fr.pacifista.api.client.server.boxes.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.boxes.dtos.BoxRewardDTO;

public class BoxRewardImplClient extends FeignImpl<BoxRewardDTO, BoxRewardClient> implements BoxRewardClient {
    public BoxRewardImplClient() {
        super("box/rewards", BoxRewardClient.class);
    }
}
