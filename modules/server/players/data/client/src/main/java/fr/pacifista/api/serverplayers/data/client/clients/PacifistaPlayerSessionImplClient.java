package fr.pacifista.api.serverplayers.data.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerSessionDTO;

public class PacifistaPlayerSessionImplClient extends FeignImpl<PacifistaPlayerSessionDTO, PacifistaPlayerSessionClient> implements PacifistaPlayerSessionClient {
    public PacifistaPlayerSessionImplClient() {
        super("playerdata/session", PacifistaPlayerSessionClient.class);
    }
}
