package fr.pacifista.api.server.permissions.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.permissions.client.clients.PacifistaPlayerRolesClient;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.server.permissions.service.services.PacifistaPlayerRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions/gameroles/player")
public class PacifistaPlayerRolesResource extends ApiResource<PacifistaPlayerRoleDTO, PacifistaPlayerRoleService> implements PacifistaPlayerRolesClient {
    public PacifistaPlayerRolesResource(PacifistaPlayerRoleService pacifistaPlayerRoleService) {
        super(pacifistaPlayerRoleService);
    }
}
