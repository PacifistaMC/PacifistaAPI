package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerExperienceData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerExperienceDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerExperienceDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PlayerExperienceDataService extends ApiService<PlayerExperienceDataDTO, PlayerExperienceData, PlayerExperienceDataMapper, PlayerExperienceDataRepository> {
    public PlayerExperienceDataService(PlayerExperienceDataRepository repository,
                                       PlayerExperienceDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PlayerExperienceData> entity) {
        for (final PlayerExperienceData playerEnderchestData : entity) {
            if (playerEnderchestData.getId() == null &&
                    super.getRepository().findByPlayerOwnerUuidAndGameMode(
                            playerEnderchestData.getPlayerOwnerUuid().toString(),
                            playerEnderchestData.getGameMode()).isPresent()
            ) {
                throw new ApiBadRequestException("PlayerExperienceData déjà existant pour ce joueur et ce mode de jeu. Joueur: " + playerEnderchestData.getPlayerOwnerUuid() + " Mode de jeu: " + playerEnderchestData.getGameMode() + ".");
            }
        }
    }
}
