package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaPlayerRoleMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPlayerRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlayerRoleService extends ApiService<PacifistaPlayerRoleDTO, PacifistaPlayerRole, PacifistaPlayerRoleMapper, PacifistaPlayerRoleRepository> {

    public PacifistaPlayerRoleService(PacifistaPlayerRoleRepository repository,
                                      PacifistaPlayerRoleMapper mapper) {
        super(repository, mapper);
    }

}
