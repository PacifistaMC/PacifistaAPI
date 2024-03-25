package fr.pacifista.api.server.essentials.service.homes.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.homes.entities.Home;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends ApiRepository<Home> {
}
