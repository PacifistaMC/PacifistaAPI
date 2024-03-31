package fr.pacifista.api.server.claim.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;

public class UserClaimAmountClientImpl extends FeignImpl<UserClaimAmountDTO, UserClaimAmountClient> {
    public static final String PATH = "claim/user-claim-amount";

    public UserClaimAmountClientImpl() {
        super(PATH, UserClaimAmountClient.class);
    }
}
