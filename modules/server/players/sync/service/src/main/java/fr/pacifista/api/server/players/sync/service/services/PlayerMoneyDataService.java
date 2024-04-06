package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerMoneyData;
import fr.pacifista.api.server.players.sync.service.mappers.PlayerMoneyDataMapper;
import fr.pacifista.api.server.players.sync.service.repositories.PlayerMoneyDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PlayerMoneyDataService extends ApiService<PlayerMoneyDataDTO, PlayerMoneyData, PlayerMoneyDataMapper, PlayerMoneyDataRepository> {
    public PlayerMoneyDataService(PlayerMoneyDataRepository repository,
                                  PlayerMoneyDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PlayerMoneyData> entity) {
        for (final PlayerMoneyData playerEnderchestData : entity) {
            if (playerEnderchestData.getId() == null &&
                    super.getRepository().findByPlayerOwnerUuid(
                            playerEnderchestData.getPlayerOwnerUuid().toString()
                    ).isPresent()
            ) {
                throw new ApiBadRequestException("PlayerMoneyData déjà existant pour ce joueur et ce mode de jeu. Joueur: " + playerEnderchestData.getPlayerOwnerUuid() + ".");
            }
        }
    }
}
