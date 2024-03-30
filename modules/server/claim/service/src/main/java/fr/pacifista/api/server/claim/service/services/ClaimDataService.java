package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ClaimDataService extends ApiService<ClaimDataDTO, ClaimData, ClaimDataMapper, ClaimDataRepository> {

    public ClaimDataService(ClaimDataRepository repository, ClaimDataMapper mapper) {
        super(repository, mapper);
    }

}
