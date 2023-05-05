package fr.pacifista.api.service.server.permissions.services;

import com.google.common.base.Strings;
import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.server.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.server.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.server.permissions.mappers.PacifistaRoleMapper;
import fr.pacifista.api.service.server.permissions.repositories.PacifistaPlayerRoleRepository;
import fr.pacifista.api.service.server.permissions.repositories.PacifistaRoleRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void beforeMappingToEntity(@NonNull Iterable<PacifistaRoleDTO> request) {
        for (final PacifistaRoleDTO roleDTO : request) {
            if (Strings.isNullOrEmpty(roleDTO.getName())) {
                throw new ApiBadRequestException("Le nom du rôle est manquant.");
            } else {
                final Optional<PacifistaRole> search = super.getRepository().findByNameIgnoreCase(roleDTO.getName());

                if (search.isPresent()) {
                    throw new ApiBadRequestException(String.format("Le rôle %s existe déjà.", roleDTO.getName()));
                }
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaRole> entity) {
        for (final PacifistaRole role : entity) {
            final List<PacifistaPlayerRole> playerRoles = pacifistaPlayerRoleRepository.findPacifistaPlayerRolesByRole(role);
            pacifistaPlayerRoleRepository.deleteAll(playerRoles);
        }
    }

    protected PacifistaRole findRole(final PacifistaRoleDTO request) {
        if (request == null) {
            throw new ApiBadRequestException("Le role ID est manquant.");
        } else {
            return this.findRole(request.getId());
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
}