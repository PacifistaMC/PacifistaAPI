package fr.pacifista.api.serverplayers.data.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.serverplayers.data.service.entities.PacifistaPlayerData;
import fr.pacifista.api.serverplayers.data.service.mappers.PacifistaPlayerDataMapper;
import fr.pacifista.api.serverplayers.data.service.repositories.PacifistaPlayerDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlayerDataService extends ApiService<PacifistaPlayerDataDTO, PacifistaPlayerData, PacifistaPlayerDataMapper, PacifistaPlayerDataRepository> {

    public PacifistaPlayerDataService(PacifistaPlayerDataRepository repository,
                                      PacifistaPlayerDataMapper mapper) {
        super(repository, mapper);
    }

}
