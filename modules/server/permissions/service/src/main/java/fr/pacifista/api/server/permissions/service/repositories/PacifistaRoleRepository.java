package fr.pacifista.api.server.permissions.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.permissions.service.entities.PacifistaRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacifistaRoleRepository extends ApiRepository<PacifistaRole> {
    Optional<PacifistaRole> findByNameIgnoreCase(String name);
}
