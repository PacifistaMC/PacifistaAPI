package fr.pacifista.api.client.server.boxes.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.boxes.dtos.PlayerBoxDTO;

public class PlayerBoxImplClient extends FeignImpl<PlayerBoxDTO, PlayerBoxClient> implements PlayerBoxClient {
    public PlayerBoxImplClient() {
        super("box/player", PlayerBoxClient.class);
    }
}
