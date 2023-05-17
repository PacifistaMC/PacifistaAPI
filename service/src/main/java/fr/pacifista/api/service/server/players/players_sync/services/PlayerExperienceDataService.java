package fr.pacifista.api.service.server.players.players_sync.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerExperienceData;
import fr.pacifista.api.service.server.players.players_sync.mappers.PlayerExperienceDataMapper;
import fr.pacifista.api.service.server.players.players_sync.repositories.PlayerExperienceDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerExperienceDataService extends ApiService<PlayerExperienceDataDTO, PlayerExperienceData, PlayerExperienceDataMapper, PlayerExperienceDataRepository> {
    public PlayerExperienceDataService(PlayerExperienceDataRepository repository,
                                       PlayerExperienceDataMapper mapper) {
        super(repository, mapper);
    }
}
