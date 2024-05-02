package fr.pacifista.api.server.players.data.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;

public class PacifistaPlayerDataImplClient extends FeignImpl<PacifistaPlayerDataDTO, PacifistaPlayerDataClient> implements PacifistaPlayerDataClient {

    public static final String PATH = "playerdata/data";
    public static final String INTERNAL_PATH = "kubeinternal/playerdata/data";

    public PacifistaPlayerDataImplClient() {
        super(PATH, PacifistaPlayerDataClient.class);
    }
}
