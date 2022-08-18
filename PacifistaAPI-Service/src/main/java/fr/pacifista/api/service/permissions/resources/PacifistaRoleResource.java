package fr.pacifista.api.service.permissions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.permissions.services.PacifistaRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameroles")
public class PacifistaRoleResource extends ApiResource<PacifistaRoleDTO, PacifistaRoleService> {
    public PacifistaRoleResource(PacifistaRoleService pacifistaRoleService) {
        super(pacifistaRoleService);
    }
}
