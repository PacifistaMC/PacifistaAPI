package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimDataUser;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataUserMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ClaimDataUserService extends ApiService<ClaimDataUserDTO, ClaimDataUser, ClaimDataUserMapper, ClaimDataUserRepository> {

    public ClaimDataUserService(ClaimDataUserRepository repository, ClaimDataUserMapper mapper) {
        super(repository, mapper);
    }

}
