package fr.pacifista.api.server.permissions.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPermissionDTO;

public class PacifistaPermissionImplClient extends FeignImpl<PacifistaPermissionDTO, PacifistaPermissionClient> implements PacifistaPermissionClient {
    public PacifistaPermissionImplClient() {
        super("permissions/gamepermissions", PacifistaPermissionClient.class);
    }
}
