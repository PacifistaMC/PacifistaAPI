package fr.pacifista.api.server.permissions.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPlayerRole;
import fr.pacifista.api.server.permissions.service.entities.PacifistaRole;
import fr.pacifista.api.server.permissions.service.mappers.PacifistaPlayerRoleMapper;
import fr.pacifista.api.server.permissions.service.repositories.PacifistaPlayerRoleRepository;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

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
    public void afterMapperCall(@NonNull PacifistaPlayerRoleDTO dto, @NonNull PacifistaPlayerRole entity) {
        if (dto.getId() == null) {
            final PacifistaRole role = roleService.findRole(dto.getRole());
            entity.setRole(role);
        }
    }

    @NotNull
    @Override
    public PacifistaPlayerRoleDTO update(PacifistaPlayerRoleDTO request) {
        throw new ApiBadRequestException("Si vous voulez mettre à jour le rôle d'un joueur. Veuillez supprimer son rôle et lui en associer un autre.");
    }

    @Override
    public List<PacifistaPlayerRoleDTO> update(List<PacifistaPlayerRoleDTO> request) {
        throw new ApiBadRequestException("Si vous voulez mettre à jour le rôle d'un joueur. Veuillez supprimer son rôle et lui en associer un autre.");
    }
}
