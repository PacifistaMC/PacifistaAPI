package fr.pacifista.api.serverplayers.data.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerSessionDTO;
import fr.pacifista.api.serverplayers.data.service.entities.PacifistaPlayerSession;
import fr.pacifista.api.serverplayers.data.service.mappers.PacifistaPlayerSessionMapper;
import fr.pacifista.api.serverplayers.data.service.repositories.PacifistaPlayerSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlayerSessionService extends ApiService<PacifistaPlayerSessionDTO, PacifistaPlayerSession, PacifistaPlayerSessionMapper, PacifistaPlayerSessionRepository> {

    public PacifistaPlayerSessionService(PacifistaPlayerSessionRepository repository,
                                         PacifistaPlayerSessionMapper mapper) {
        super(repository, mapper);
    }

}
