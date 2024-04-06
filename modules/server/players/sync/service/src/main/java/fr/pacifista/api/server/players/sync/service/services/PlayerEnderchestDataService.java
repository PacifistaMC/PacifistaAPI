package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerEnderchestData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerEnderchestDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerEnderchestDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PlayerEnderchestDataService extends ApiService<PlayerEnderchestDataDTO, PlayerEnderchestData, PlayerEnderchestDataMapper, PlayerEnderchestDataRepository> {

    public PlayerEnderchestDataService(PlayerEnderchestDataRepository repository,
                                       PlayerEnderchestDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PlayerEnderchestData> entity) {
        for (final PlayerEnderchestData playerEnderchestData : entity) {
            if (playerEnderchestData.getId() == null &&
                    super.getRepository().findByPlayerOwnerUuidAndGameMode(
                            playerEnderchestData.getPlayerOwnerUuid().toString(),
                            playerEnderchestData.getGameMode()).isPresent()
            ) {
                throw new ApiBadRequestException("PlayerEnderchestData déjà existant pour ce joueur et ce mode de jeu. Joueur: " + playerEnderchestData.getPlayerOwnerUuid() + " Mode de jeu: " + playerEnderchestData.getGameMode() + ".");
            }
        }
    }
}
