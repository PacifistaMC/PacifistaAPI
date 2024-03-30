package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimDataConfig;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataConfigMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class ClaimDataConfigService extends ApiService<ClaimDataConfigDTO, ClaimDataConfig, ClaimDataConfigMapper, ClaimDataConfigRepository> {

    public ClaimDataConfigService(ClaimDataConfigRepository repository, ClaimDataConfigMapper mapper) {
        super(repository, mapper);
    }

}
