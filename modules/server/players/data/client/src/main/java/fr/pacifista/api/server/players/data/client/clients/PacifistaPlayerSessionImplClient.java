package fr.pacifista.api.server.players.data.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerSessionDTO;

public class PacifistaPlayerSessionImplClient extends FeignImpl<PacifistaPlayerSessionDTO, PacifistaPlayerSessionClient> implements PacifistaPlayerSessionClient {
    public PacifistaPlayerSessionImplClient() {
        super("playerdata/session", PacifistaPlayerSessionClient.class);
    }
}
