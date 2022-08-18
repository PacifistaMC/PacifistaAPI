package fr.pacifista.api.service.permissions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPlayerRoleRepository extends ApiRepository<PacifistaPlayerRole> {
}
