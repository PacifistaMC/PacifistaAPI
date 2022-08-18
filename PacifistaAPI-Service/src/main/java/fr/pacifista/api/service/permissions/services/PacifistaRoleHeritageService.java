package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleHeritageDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import fr.pacifista.api.service.permissions.mappers.PacifistaRoleHeritageMapper;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleHeritageRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaRoleHeritageService extends ApiService<PacifistaRoleHeritageDTO, PacifistaRoleHeritage, PacifistaRoleHeritageMapper, PacifistaRoleHeritageRepository> {

    public PacifistaRoleHeritageService(PacifistaRoleHeritageRepository repository,
                                        PacifistaRoleHeritageMapper mapper) {
        super(repository, mapper);
    }

}
