package fr.pacifista.api.server.essentials.service.ignore_players.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.ignore_players.entities.PlayerIgnore;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerIgnoreRepository extends ApiRepository<PlayerIgnore> {
}
