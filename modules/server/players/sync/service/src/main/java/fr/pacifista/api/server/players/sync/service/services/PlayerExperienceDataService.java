package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerExperienceData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerExperienceDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerExperienceDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerExperienceDataService extends ApiService<PlayerExperienceDataDTO, PlayerExperienceData, PlayerExperienceDataMapper, PlayerExperienceDataRepository> {
    public PlayerExperienceDataService(PlayerExperienceDataRepository repository,
                                       PlayerExperienceDataMapper mapper) {
        super(repository, mapper);
    }
}
