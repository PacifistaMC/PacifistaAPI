package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleHeritageDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import fr.pacifista.api.service.permissions.mappers.PacifistaRoleHeritageMapper;
import fr.pacifista.api.service.permissions.mappers.PacifistaRoleMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPlayerRoleRepository;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleHeritageRepository;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacifistaRoleService extends ApiService<PacifistaRoleDTO, PacifistaRole, PacifistaRoleMapper, PacifistaRoleRepository> {

    private final PacifistaPlayerRoleRepository pacifistaPlayerRoleRepository;

    private final PacifistaRoleHeritageRepository roleHeritageRepository;
    private final PacifistaRoleHeritageMapper roleHeritageMapper;

    public PacifistaRoleService(PacifistaRoleRepository repository,
                                PacifistaPlayerRoleRepository pacifistaPlayerRoleRepository,
                                PacifistaRoleHeritageRepository roleHeritageRepository,
                                PacifistaRoleHeritageMapper roleHeritageMapper,
                                PacifistaRoleMapper mapper) {
        super(repository, mapper);
        this.pacifistaPlayerRoleRepository = pacifistaPlayerRoleRepository;
        this.roleHeritageRepository = roleHeritageRepository;
        this.roleHeritageMapper = roleHeritageMapper;
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
            this.roleHeritageRepository.deleteAll(this.roleHeritageRepository.findAllByRole(role));
            this.roleHeritageRepository.deleteAll(this.roleHeritageRepository.findAllByHeritage(role));
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

    @Transactional
    public PacifistaRoleHeritageDTO addHeritage(final PacifistaRoleHeritageDTO request) {
        final PacifistaRole role = findRole(request.getRole());
        final PacifistaRole heritage = findRole(request.getHeritage());
        final PacifistaRoleHeritage entity = roleHeritageMapper.toEntity(request);

        entity.setRole(role);
        entity.setHeritage(heritage);
        return roleHeritageMapper.toDto(roleHeritageRepository.save(entity));
    }

    public void removeHeritage(final String heritageId) {
        if (Strings.isEmpty(heritageId)) {
            throw new ApiBadRequestException("L'id de l'héritage ID est manquant.");
        } else {
            final Optional<PacifistaRoleHeritage> search = roleHeritageRepository.findByUuid(heritageId);

            if (search.isPresent()) {
                roleHeritageRepository.delete(search.get());
            } else {
                throw new ApiNotFoundException(String.format("L'héritage ID %s n'existe pas.", heritageId));
            }
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
        if (request.getId() == null) {
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
