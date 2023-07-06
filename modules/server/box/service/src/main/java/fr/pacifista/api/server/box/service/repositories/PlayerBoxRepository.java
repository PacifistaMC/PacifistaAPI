package fr.pacifista.api.server.box.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.box.service.entities.Box;
import fr.pacifista.api.server.box.service.entities.PlayerBox;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerBoxRepository extends ApiRepository<PlayerBox> {
    List<PlayerBox> findAllByBox(Box box);
    Optional<PlayerBox> findPlayerBoxByBoxAndPlayerUuid(Box box, String playerUuid);
}
