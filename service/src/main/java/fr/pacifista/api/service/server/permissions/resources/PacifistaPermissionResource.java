package fr.pacifista.api.service.server.permissions.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.permissions.clients.PacifistaPermissionClient;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.server.permissions.services.PacifistaPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gamepermissions")
public class PacifistaPermissionResource extends ApiResource<PacifistaPermissionDTO, PacifistaPermissionService> implements PacifistaPermissionClient {
    public PacifistaPermissionResource(PacifistaPermissionService pacifistaPermissionService) {
        super(pacifistaPermissionService);
    }
}
