package fr.pacifista.api.server.essentials.client.holograms.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.holograms.dtos.HologramDTO;

public class HologramClientImpl extends FeignImpl<HologramDTO, HologramClient> implements HologramClient {

    public static final String PATH = "essentials/hologram";

    public HologramClientImpl() {
        super(PATH, HologramClient.class);
    }

}
