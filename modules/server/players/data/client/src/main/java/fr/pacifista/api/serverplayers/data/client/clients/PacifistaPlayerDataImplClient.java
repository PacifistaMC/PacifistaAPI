package fr.pacifista.api.serverplayers.data.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;

public class PacifistaPlayerDataImplClient extends FeignImpl<PacifistaPlayerDataDTO, PacifistaPlayerDataClient> implements PacifistaPlayerDataClient {
    public PacifistaPlayerDataImplClient() {
        super("playerdata/data", PacifistaPlayerDataClient.class);
    }
}
