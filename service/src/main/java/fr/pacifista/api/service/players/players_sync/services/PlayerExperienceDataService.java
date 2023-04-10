package fr.pacifista.api.service.players.players_sync.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.service.players.players_sync.entities.PlayerExperienceData;
import fr.pacifista.api.service.players.players_sync.mappers.PlayerExperienceDataMapper;
import fr.pacifista.api.service.players.players_sync.repositories.PlayerExperienceDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerExperienceDataService extends ApiService<PlayerExperienceDataDTO, PlayerExperienceData, PlayerExperienceDataMapper, PlayerExperienceDataRepository> {
    public PlayerExperienceDataService(PlayerExperienceDataRepository repository,
                                       PlayerExperienceDataMapper mapper) {
        super(repository, mapper);
    }
}
