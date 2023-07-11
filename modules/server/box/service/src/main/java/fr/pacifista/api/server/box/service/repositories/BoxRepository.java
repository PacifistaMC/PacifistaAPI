package fr.pacifista.api.server.box.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.box.service.entities.Box;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends ApiRepository<Box> {
}
