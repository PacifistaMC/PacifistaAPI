package fr.pacifista.api.client.boxes.clients;

import fr.pacifista.api.client.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;

public class BoxRewardImplClient extends FeignImpl<BoxRewardDTO, BoxRewardClient> implements BoxRewardClient {
    public BoxRewardImplClient() {
        super("box/rewards", BoxRewardClient.class);
    }
}
