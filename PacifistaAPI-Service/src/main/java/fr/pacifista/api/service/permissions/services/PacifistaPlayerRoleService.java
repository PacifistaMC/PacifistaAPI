package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.mappers.PacifistaPlayerRoleMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaPlayerRoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacifistaPlayerRoleService extends ApiService<PacifistaPlayerRoleDTO, PacifistaPlayerRole, PacifistaPlayerRoleMapper, PacifistaPlayerRoleRepository> {

    private final PacifistaRoleService roleService;

    public PacifistaPlayerRoleService(PacifistaPlayerRoleRepository repository,
                                      PacifistaRoleService roleService,
                                      PacifistaPlayerRoleMapper mapper) {
        super(repository, mapper);
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public PacifistaPlayerRoleDTO create(PacifistaPlayerRoleDTO request) {
        if (request.getRole().getId() == null) {
            throw new ApiBadRequestException("Le role ID est manquant.");
        }

        final PacifistaRole role = roleService.findRole(request.getRole());
        final PacifistaRole staffRole;

        if (request.getStaffRole() != null) {
            staffRole = roleService.findRole(request.getStaffRole());
        } else {
            staffRole = null;
        }

        final PacifistaPlayerRole playerRole = getMapper().toEntity(request);
        playerRole.setRole(role);
        playerRole.setStaffRole(staffRole);

        return getMapper().toDto(getRepository().save(playerRole));
    }

    @Override
    @Transactional
    public PacifistaPlayerRoleDTO update(PacifistaPlayerRoleDTO request) {
        if (request.getId() == null) {
            throw new ApiBadRequestException("L'id du role du joueur est manquant.");
        }

        final Optional<PacifistaPlayerRole> search = getRepository().findByUuid(request.getId().toString());
        if (search.isPresent()) {
            final PacifistaRole role = roleService.findRole(request.getRole());
            final PacifistaRole staffRole;

            if (request.getStaffRole() != null) {
                staffRole = roleService.findRole(request.getStaffRole());
            } else {
                staffRole = null;
            }

            final PacifistaPlayerRole entity = search.get();
            final PacifistaPlayerRole entRequest = getMapper().toEntity(request);

            entRequest.setId(null);
            entRequest.setUpdatedAt(Date.from(Instant.now()));
            getMapper().patch(entRequest, entity);
            entity.setRole(role);
            entity.setStaffRole(staffRole);

            return getMapper().toDto(getRepository().save(entity));
        } else {
            throw new ApiBadRequestException(String.format("Le role de joueur ID %s n'existe pas.", request.getId()));
        }
    }

    @Override
    public List<PacifistaPlayerRoleDTO> update(List<PacifistaPlayerRoleDTO> request) {
        final List<PacifistaPlayerRoleDTO> toSend = new ArrayList<>();

        for (final PacifistaPlayerRoleDTO roleDTO : request) {
            toSend.add(update(roleDTO));
        }
        return toSend;
    }
}
