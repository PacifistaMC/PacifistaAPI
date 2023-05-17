package fr.pacifista.api.client.server.permissions.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaPermissionDTO;

public class PacifistaPermissionImplClient extends FeignImpl<PacifistaPermissionDTO, PacifistaPermissionClient> implements PacifistaPermissionClient {
    public PacifistaPermissionImplClient() {
        super("gamepermissions", PacifistaPermissionClient.class);
    }
}
