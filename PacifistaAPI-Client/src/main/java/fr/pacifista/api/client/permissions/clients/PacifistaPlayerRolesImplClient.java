package fr.pacifista.api.client.permissions.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;

public class PacifistaPlayerRolesImplClient extends FeignImpl<PacifistaPlayerRoleDTO, PacifistaPlayerRolesClient> implements PacifistaPlayerRolesClient {
    public PacifistaPlayerRolesImplClient() {
        super("gameroles/player", PacifistaPlayerRolesClient.class);
    }
}
