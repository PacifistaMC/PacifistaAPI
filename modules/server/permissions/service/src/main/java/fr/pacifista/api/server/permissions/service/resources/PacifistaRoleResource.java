package fr.pacifista.api.server.permissions.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.permissions.client.clients.PacifistaRoleClient;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaRoleDTO;
import fr.pacifista.api.server.permissions.service.services.PacifistaRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permissions/gameroles")
public class PacifistaRoleResource extends ApiResource<PacifistaRoleDTO, PacifistaRoleService> implements PacifistaRoleClient {
    public PacifistaRoleResource(PacifistaRoleService pacifistaRoleService) {
        super(pacifistaRoleService);
    }
}
