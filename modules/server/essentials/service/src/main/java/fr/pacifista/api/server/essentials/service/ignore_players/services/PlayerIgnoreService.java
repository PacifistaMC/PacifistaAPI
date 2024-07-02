package fr.pacifista.api.server.essentials.service.ignore_players.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;
import fr.pacifista.api.server.essentials.service.ignore_players.entities.PlayerIgnore;
import fr.pacifista.api.server.essentials.service.ignore_players.mappers.PlayerIgnoreMapper;
import fr.pacifista.api.server.essentials.service.ignore_players.repositories.PlayerIgnoreRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerIgnoreService extends ApiService<PlayerIgnoreDTO, PlayerIgnore, PlayerIgnoreMapper, PlayerIgnoreRepository> {

    public PlayerIgnoreService(PlayerIgnoreRepository repository, PlayerIgnoreMapper mapper) {
        super(repository, mapper);
    }

}
