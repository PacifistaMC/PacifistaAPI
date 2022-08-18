package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaRoleMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaRoleService extends ApiService<PacifistaRoleDTO, PacifistaRole, PacifistaRoleMapper, PacifistaRoleRepository> {

    public PacifistaRoleService(PacifistaRoleRepository repository,
                                PacifistaRoleMapper mapper) {
        super(repository, mapper);
    }


}
