package fr.pacifista.api.service.server.permissions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.permissions.clients.PacifistaRoleClient;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.server.permissions.services.PacifistaRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameroles")
public class PacifistaRoleResource extends ApiResource<PacifistaRoleDTO, PacifistaRoleService> implements PacifistaRoleClient {
    public PacifistaRoleResource(PacifistaRoleService pacifistaRoleService) {
        super(pacifistaRoleService);
    }
}
