package fr.pacifista.api.server.permissions.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPermission;
import fr.pacifista.api.server.permissions.service.entities.PacifistaRole;
import fr.pacifista.api.server.permissions.service.mappers.PacifistaPermissionMapper;
import fr.pacifista.api.server.permissions.service.repositories.PacifistaPermissionRepository;
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
    public void afterMapperCall(@NonNull PacifistaPermissionDTO dto, @NonNull PacifistaPermission entity) {
        final PacifistaRole role = pacifistaRoleService.findRole(dto.getRoleId());
        entity.setRole(role);

        pacifistaRoleService.getRepository().save(role);
    }

}
