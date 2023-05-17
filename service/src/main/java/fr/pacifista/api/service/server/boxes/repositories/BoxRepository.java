package fr.pacifista.api.service.server.boxes.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.boxes.entities.Box;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends ApiRepository<Box> {
}
