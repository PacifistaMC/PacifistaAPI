package fr.pacifista.api.client.permissions.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;

public class PacifistaRoleImplClient extends FeignImpl<PacifistaRoleDTO, PacifistaRoleClient> implements PacifistaRoleClient {
    public PacifistaRoleImplClient() {
        super("gameroles", PacifistaRoleClient.class);
    }
}
