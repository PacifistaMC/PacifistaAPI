package fr.pacifista.api.client.boxes.clients;

import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;

public class BoxImplClient extends FeignImpl<BoxDTO, BoxClient> implements BoxClient {
    public BoxImplClient() {
        super("box", BoxClient.class);
    }
}
