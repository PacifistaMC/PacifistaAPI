package fr.pacifista.api.web.user.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebLegal;
import fr.pacifista.api.web.user.service.mappers.PacifistaWebLegalMapper;
import fr.pacifista.api.web.user.service.repositories.PacifistaWebLegalRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaWebLegalService extends ApiService<PacifistaWebLegalDTO, PacifistaWebLegal, PacifistaWebLegalMapper, PacifistaWebLegalRepository> {

    public PacifistaWebLegalService(PacifistaWebLegalRepository repository,
                                    PacifistaWebLegalMapper mapper) {
        super(repository, mapper);
    }

}
