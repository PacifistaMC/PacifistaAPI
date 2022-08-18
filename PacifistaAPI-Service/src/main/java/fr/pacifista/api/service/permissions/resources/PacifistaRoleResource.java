package fr.pacifista.api.service.permissions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.permissions.clients.PacifistaRoleClient;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleHeritageDTO;
import fr.pacifista.api.service.permissions.services.PacifistaRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gameroles")
public class PacifistaRoleResource extends ApiResource<PacifistaRoleDTO, PacifistaRoleService> implements PacifistaRoleClient {

    public PacifistaRoleResource(PacifistaRoleService pacifistaRoleService) {
        super(pacifistaRoleService);
    }

    @Override
    public PacifistaRoleHeritageDTO addHeritage(PacifistaRoleHeritageDTO request) {
        return getService().addHeritage(request);
    }

    @Override
    public void removeHeritage(String heritageId) {
        getService().removeHeritage(heritageId);
    }
}
