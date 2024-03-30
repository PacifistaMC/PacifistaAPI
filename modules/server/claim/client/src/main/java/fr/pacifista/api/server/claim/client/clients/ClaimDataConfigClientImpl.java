package fr.pacifista.api.server.claim.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;

public class ClaimDataConfigClientImpl extends FeignImpl<ClaimDataConfigDTO, ClaimDataConfigClient> {
    public static final String PATH = "claim/claim-data-config";

    public ClaimDataConfigClientImpl() {
        super(PATH, ClaimDataConfigClient.class);
    }
}
