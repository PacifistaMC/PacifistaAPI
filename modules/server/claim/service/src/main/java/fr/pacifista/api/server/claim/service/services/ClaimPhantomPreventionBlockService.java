package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimPhantomPreventionBlock;
import fr.pacifista.api.server.claim.service.mappers.ClaimPhantomPreventionBlockMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimPhantomPreventionBlockRepository;
import org.springframework.stereotype.Service;

@Service
public class ClaimPhantomPreventionBlockService extends ApiService<ClaimPhantomPreventionBlockDTO, ClaimPhantomPreventionBlock, ClaimPhantomPreventionBlockMapper, ClaimPhantomPreventionBlockRepository> {

    public ClaimPhantomPreventionBlockService(ClaimPhantomPreventionBlockRepository repository,
                                              ClaimPhantomPreventionBlockMapper mapper) {
        super(repository, mapper);
    }

}
