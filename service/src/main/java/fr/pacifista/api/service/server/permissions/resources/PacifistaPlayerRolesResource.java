package fr.pacifista.api.service.server.permissions.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.permissions.clients.PacifistaPlayerRolesClient;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.service.server.permissions.services.PacifistaPlayerRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameroles/player")
public class PacifistaPlayerRolesResource extends ApiResource<PacifistaPlayerRoleDTO, PacifistaPlayerRoleService> implements PacifistaPlayerRolesClient {
    public PacifistaPlayerRolesResource(PacifistaPlayerRoleService pacifistaPlayerRoleService) {
        super(pacifistaPlayerRoleService);
    }
}
