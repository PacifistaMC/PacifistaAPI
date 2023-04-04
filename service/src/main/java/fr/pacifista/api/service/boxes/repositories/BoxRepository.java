package fr.pacifista.api.service.boxes.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.boxes.entities.Box;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends ApiRepository<Box> {
}
