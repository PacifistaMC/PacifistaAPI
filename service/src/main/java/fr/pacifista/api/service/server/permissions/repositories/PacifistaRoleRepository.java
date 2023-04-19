package fr.pacifista.api.service.server.permissions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.permissions.entities.PacifistaRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacifistaRoleRepository extends ApiRepository<PacifistaRole> {
    Optional<PacifistaRole> findByNameIgnoreCase(String name);
}
