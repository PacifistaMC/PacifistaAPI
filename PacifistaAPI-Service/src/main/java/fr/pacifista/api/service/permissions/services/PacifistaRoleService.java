package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaRoleMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPlayerRoleRepository;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PacifistaRoleService extends ApiService<PacifistaRoleDTO, PacifistaRole, PacifistaRoleMapper, PacifistaRoleRepository> {

    private final PacifistaPlayerRoleRepository pacifistaPlayerRoleRepository;

    public PacifistaRoleService(PacifistaRoleRepository repository,
                                PacifistaPlayerRoleRepository pacifistaPlayerRoleRepository,
                                PacifistaRoleMapper mapper) {
        super(repository, mapper);
        this.pacifistaPlayerRoleRepository = pacifistaPlayerRoleRepository;
    }

    @Override
    public void delete(String id) {
        if (Strings.isEmpty(id)) {
            throw new ApiBadRequestException("Pas d'id spécifié pour la suppresion d'un role");
        }

        final Optional<PacifistaRole> search = super.getRepository().findByUuid(id);
        if (search.isPresent()) {
            final PacifistaRole role = search.get();

            this.pacifistaPlayerRoleRepository.deleteAll(this.pacifistaPlayerRoleRepository.findPacifistaPlayerRolesByRole(role));
            super.getRepository().delete(role);
        } else {
            throw new ApiNotFoundException(String.format("Le role id %s n'existe pas.", id));
        }
    }

    @Override
    public void delete(String... ids) {
        for (String id : ids) {
            delete(id);
        }
    }

    protected PacifistaRole findRole(final UUID roleUuid) {
        if (roleUuid == null) {
            throw new ApiBadRequestException("Le role ID est manquant");
        } else {
            final Optional<PacifistaRole> search = super.getRepository().findByUuid(roleUuid.toString());

            if (search.isPresent()) {
                return search.get();
            } else {
                throw new ApiNotFoundException(String.format("Le role ID %s n'existe pas.", roleUuid));
            }
        }
    }

    protected PacifistaRole findRole(final PacifistaRoleDTO request) {
        if (request == null || request.getId() == null) {
            throw new ApiBadRequestException("Le role ID est manquant.");
        } else {
            final Optional<PacifistaRole> search = super.getRepository().findByUuid(request.getId().toString());

            if (search.isPresent()) {
                return search.get();
            } else {
                throw new ApiNotFoundException(String.format("Le role ID %s n'existe pas.", request.getId()));
            }
        }
    }
}
