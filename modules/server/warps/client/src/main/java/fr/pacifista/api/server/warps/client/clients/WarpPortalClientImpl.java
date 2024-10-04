package fr.pacifista.api.server.warps.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;

public class WarpPortalClientImpl extends FeignImpl<WarpPortalDTO, WarpPortalClient> {

    public static final String PATH = "warps/portals";

    public WarpPortalClientImpl() {
        super(PATH, WarpPortalClient.class);
    }

}
