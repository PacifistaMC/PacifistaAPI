package fr.pacifista.api.server.permissions.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.permissions.client.clients.PacifistaPermissionClient;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.server.permissions.service.services.PacifistaPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions/gamepermissions")
public class PacifistaPermissionResource extends ApiResource<PacifistaPermissionDTO, PacifistaPermissionService> implements PacifistaPermissionClient {
    public PacifistaPermissionResource(PacifistaPermissionService pacifistaPermissionService) {
        super(pacifistaPermissionService);
    }
}
