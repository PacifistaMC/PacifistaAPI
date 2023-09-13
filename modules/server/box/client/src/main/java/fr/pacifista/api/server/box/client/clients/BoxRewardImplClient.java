package fr.pacifista.api.server.box.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;

public class BoxRewardImplClient extends FeignImpl<BoxRewardDTO, BoxRewardClient> implements BoxRewardClient {

    public static final String PATH = "box/rewards";

    public BoxRewardImplClient() {
        super(PATH, BoxRewardClient.class);
    }
}
