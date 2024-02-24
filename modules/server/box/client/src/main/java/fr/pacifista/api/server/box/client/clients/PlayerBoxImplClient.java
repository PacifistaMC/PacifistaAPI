package fr.pacifista.api.server.box.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;

public class PlayerBoxImplClient extends FeignImpl<PlayerBoxDTO, PlayerBoxClient> implements PlayerBoxClient {

    public static final String PATH = "box/player";

    public PlayerBoxImplClient() {
        super(PATH, PlayerBoxClient.class);
    }
}
