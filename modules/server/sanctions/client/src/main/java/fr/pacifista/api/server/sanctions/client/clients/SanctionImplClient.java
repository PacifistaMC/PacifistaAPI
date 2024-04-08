package fr.pacifista.api.server.sanctions.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;

public class SanctionImplClient extends FeignImpl<SanctionDTO, SanctionClient> implements SanctionClient {
    public SanctionImplClient() {
        super("sanctions", SanctionClient.class);
    }
}
