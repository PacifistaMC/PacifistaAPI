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
import java.util.ArrayList;
import java.util.List;

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
        if (request.getRole() == null || request.getRole().getId() == null) {
            throw new ApiBadRequestException("Le role ID est manquant.");
        }

        final PacifistaRole role = roleService.findRole(request.getRole());
        final PacifistaPlayerRole playerRole = getMapper().toEntity(request);

        playerRole.setRole(role);
        return getMapper().toDto(getRepository().save(playerRole));
    }

    @Override
    public PacifistaPlayerRoleDTO update(PacifistaPlayerRoleDTO request) {
        throw new ApiBadRequestException("Si vous voulez mettre à jour le rôle d'un joueur. Veuillez supprimer son rôle et lui en associer un autre.");
    }

    @Override
    public List<PacifistaPlayerRoleDTO> update(List<PacifistaPlayerRoleDTO> request) {
        throw new ApiBadRequestException("Si vous voulez mettre à jour le rôle d'un joueur. Veuillez supprimer son rôle et lui en associer un autre.");
    }

    @Transactional
    public List<PacifistaPlayerRoleDTO> getPlayerRoles(final String playerUuid) {
        final List<PacifistaPlayerRoleDTO> toSend = new ArrayList<>();

        for (final PacifistaPlayerRole role : getRepository().findPacifistaPlayerRolesByPlayerUuid(playerUuid)) {
            toSend.add(getMapper().toDto(role));
        }
        return toSend;
    }
}
