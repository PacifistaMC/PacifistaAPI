package fr.pacifista.api.client.server.boxes.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.boxes.dtos.BoxDTO;

public class BoxImplClient extends FeignImpl<BoxDTO, BoxClient> implements BoxClient {
    public BoxImplClient() {
        super("box", BoxClient.class);
    }
}
