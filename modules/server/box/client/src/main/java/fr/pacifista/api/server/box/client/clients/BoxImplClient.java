package fr.pacifista.api.server.box.client.clients;


import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;

public class BoxImplClient extends FeignImpl<BoxDTO, BoxClient> implements BoxClient {

    public static final String PATH = "box";

    public BoxImplClient() {
        super(PATH, BoxClient.class);
    }
}
