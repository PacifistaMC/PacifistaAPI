package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import fr.pacifista.api.service.permissions.mappers.PacifistaPermissionMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPermissionService extends ApiService<PacifistaPermissionDTO, PacifistaPermission, PacifistaPermissionMapper, PacifistaPermissionRepository> {

    public PacifistaPermissionService(PacifistaPermissionRepository repository,
                                      PacifistaPermissionMapper mapper) {
        super(repository, mapper);
    }

}
