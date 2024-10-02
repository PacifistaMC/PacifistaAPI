package fr.pacifista.api.server.claim.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;

public class ClaimPhantomPreventionBlockClientImpl extends FeignImpl<ClaimPhantomPreventionBlockDTO, ClaimPhantomPreventionBlockClient> {

    public static final String PATH = "claim/phantom-prevention-blocks";

    public ClaimPhantomPreventionBlockClientImpl() {
        super(PATH, ClaimPhantomPreventionBlockClient.class);
    }

}
