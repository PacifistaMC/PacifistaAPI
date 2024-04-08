package fr.pacifista.api.server.sanctions.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.service.entities.Sanction;
import fr.pacifista.api.server.sanctions.service.mappers.SanctionMapper;
import fr.pacifista.api.server.sanctions.service.repositories.SanctionRepository;
import org.springframework.stereotype.Service;

@Service
public class SanctionService extends ApiService<SanctionDTO, Sanction, SanctionMapper, SanctionRepository> {

    public SanctionService(SanctionRepository repository,
                           SanctionMapper mapper) {
        super(repository, mapper);
    }

}
