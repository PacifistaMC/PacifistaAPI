package fr.pacifista.api.server.warps.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;

public class WarpPlayerInteractionClientImpl extends FeignImpl<WarpPlayerInteractionDTO, WarpPlayerInteractionClient> {

    public static final String PATH = "warps/interactions";

    public WarpPlayerInteractionClientImpl() {
        super(PATH, WarpPlayerInteractionClient.class);
    }

}
