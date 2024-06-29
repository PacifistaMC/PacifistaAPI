package fr.pacifista.api.web.user.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebLegalUser;
import fr.pacifista.api.web.user.service.mappers.PacifistaWebLegalUserMapper;
import fr.pacifista.api.web.user.service.repositories.PacifistaWebLegalUserRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaWebLegalUserService extends ApiService<PacifistaWebLegalUserDTO, PacifistaWebLegalUser, PacifistaWebLegalUserMapper, PacifistaWebLegalUserRepository> {

    public PacifistaWebLegalUserService(PacifistaWebLegalUserRepository repository,
                                        PacifistaWebLegalUserMapper mapper) {
        super(repository, mapper);
    }

}
