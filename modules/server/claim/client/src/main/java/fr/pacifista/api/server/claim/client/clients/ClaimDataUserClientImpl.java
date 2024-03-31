package fr.pacifista.api.server.claim.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;

public class ClaimDataUserClientImpl extends FeignImpl<ClaimDataUserDTO, ClaimDataUserClient> {

    public static final String PATH = "claim/claim-data-user";

    public ClaimDataUserClientImpl() {
        super(PATH, ClaimDataUserClient.class);
    }

}
