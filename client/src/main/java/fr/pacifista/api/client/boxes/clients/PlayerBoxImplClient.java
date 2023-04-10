package fr.pacifista.api.client.boxes.clients;

import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;

public class PlayerBoxImplClient extends FeignImpl<PlayerBoxDTO, PlayerBoxClient> implements PlayerBoxClient {
    public PlayerBoxImplClient() {
        super("box/player", PlayerBoxClient.class);
    }
}
