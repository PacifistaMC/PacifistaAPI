package fr.pacifista.api.server.claim.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;

public class ClaimDataClientImpl extends FeignImpl<ClaimDataDTO, ClaimDataClient> {
    public static final String PATH = "claim/claim-data";

    public ClaimDataClientImpl() {
        super(PATH, ClaimDataClient.class);
    }
}
