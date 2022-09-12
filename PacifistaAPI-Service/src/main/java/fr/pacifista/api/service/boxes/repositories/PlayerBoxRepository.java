package fr.pacifista.api.service.boxes.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.boxes.entities.Box;
import fr.pacifista.api.service.boxes.entities.PlayerBox;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerBoxRepository extends ApiRepository<PlayerBox> {
    List<PlayerBox> findAllByBox(Box box);
    Optional<PlayerBox> findPlayerBoxByBoxAndPlayerUuid(Box box, String playerUuid);
}
