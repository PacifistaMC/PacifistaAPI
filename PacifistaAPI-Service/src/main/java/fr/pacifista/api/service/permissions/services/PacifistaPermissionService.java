package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaPermissionMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPermissionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPermissionService extends ApiService<PacifistaPermissionDTO, PacifistaPermission, PacifistaPermissionMapper, PacifistaPermissionRepository> {

    private final PacifistaRoleService pacifistaRoleService;

    public PacifistaPermissionService(PacifistaPermissionRepository repository,
                                      PacifistaRoleService pacifistaRoleService,
                                      PacifistaPermissionMapper mapper) {
        super(repository, mapper);
        this.pacifistaRoleService = pacifistaRoleService;
    }

    @Override
    public void beforeSavingEntity(@NonNull PacifistaPermissionDTO request, @NonNull PacifistaPermission entity) {
        if (request.getId() == null) {
            final PacifistaRole role = pacifistaRoleService.findRole(request.getRoleId());
            entity.setRole(role);
        }
    }

}
