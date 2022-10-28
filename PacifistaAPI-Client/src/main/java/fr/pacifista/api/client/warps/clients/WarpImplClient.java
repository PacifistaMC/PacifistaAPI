package fr.pacifista.api.client.warps.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.warps.dtos.WarpDTO;

public class WarpImplClient extends FeignImpl<WarpDTO, WarpClient> implements WarpClient {
    public WarpImplClient() {
        super("warps", WarpClient.class);
    }
}
