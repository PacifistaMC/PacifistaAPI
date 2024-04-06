package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerInventoryData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerInventoryDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerInventoryDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PlayerInventoryDataService extends ApiService<PlayerInventoryDataDTO, PlayerInventoryData, PlayerInventoryDataMapper, PlayerInventoryDataRepository> {
    public PlayerInventoryDataService(PlayerInventoryDataRepository repository, PlayerInventoryDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PlayerInventoryData> entity) {
        for (final PlayerInventoryData playerEnderchestData : entity) {
            if (playerEnderchestData.getId() == null &&
                    super.getRepository().findByPlayerOwnerUuidAndGameMode(
                            playerEnderchestData.getPlayerOwnerUuid().toString(),
                            playerEnderchestData.getGameMode()).isPresent()
            ) {
                throw new ApiBadRequestException("PlayerInventoryData déjà existant pour ce joueur et ce mode de jeu. Joueur: " + playerEnderchestData.getPlayerOwnerUuid() + " Mode de jeu: " + playerEnderchestData.getGameMode() + ".");
            }
        }
    }
}
