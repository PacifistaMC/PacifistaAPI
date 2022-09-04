package fr.pacifista.api.service.permissions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.permissions.clients.PacifistaPermissionClient;
import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.services.PacifistaPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gamepermissions")
public class PacifistaPermissionResource extends ApiResource<PacifistaPermissionDTO, PacifistaPermissionService> implements PacifistaPermissionClient {
    public PacifistaPermissionResource(PacifistaPermissionService pacifistaPermissionService) {
        super(pacifistaPermissionService);
    }
}
