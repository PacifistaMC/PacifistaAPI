package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaPermissionMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPermissionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public PacifistaPermissionDTO create(PacifistaPermissionDTO request) {
        if (request.getRoleId() == null) {
            throw new ApiBadRequestException("Id du role est manquant.");
        }

        final PacifistaRole role = pacifistaRoleService.findRole(request.getRoleId());
        final PacifistaPermission pacifistaPermission = super.getMapper().toEntity(request);

        pacifistaPermission.setRole(role);
        return getMapper().toDto(getRepository().save(pacifistaPermission));
    }

}
