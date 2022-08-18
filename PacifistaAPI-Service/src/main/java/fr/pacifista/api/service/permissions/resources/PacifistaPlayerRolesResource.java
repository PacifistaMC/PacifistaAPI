package fr.pacifista.api.service.permissions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.service.permissions.services.PacifistaPlayerRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameroles/player")
public class PacifistaPlayerRolesResource extends ApiResource<PacifistaPlayerRoleDTO, PacifistaPlayerRoleService> {
    public PacifistaPlayerRolesResource(PacifistaPlayerRoleService pacifistaPlayerRoleService) {
        super(pacifistaPlayerRoleService);
    }
}
