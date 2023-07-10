package fr.pacifista.api.server.permissions.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaRoleDTO;

public class PacifistaRoleImplClient extends FeignImpl<PacifistaRoleDTO, PacifistaRoleClient> implements PacifistaRoleClient {
    public PacifistaRoleImplClient() {
        super("permissions/gameroles", PacifistaRoleClient.class);
    }
}
