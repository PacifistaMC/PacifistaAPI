package fr.pacifista.api.server.permissions.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPlayerRoleDTO;

public class PacifistaPlayerRolesImplClient extends FeignImpl<PacifistaPlayerRoleDTO, PacifistaPlayerRolesClient> implements PacifistaPlayerRolesClient {
    public PacifistaPlayerRolesImplClient() {
        super("permissions/gameroles/player", PacifistaPlayerRolesClient.class);
    }
}
