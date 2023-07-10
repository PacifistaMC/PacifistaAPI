package fr.pacifista.api.server.warps.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;

public class WarpImplClient extends FeignImpl<WarpDTO, WarpClient> implements WarpClient {
    public WarpImplClient() {
        super("warps", WarpClient.class);
    }
}
