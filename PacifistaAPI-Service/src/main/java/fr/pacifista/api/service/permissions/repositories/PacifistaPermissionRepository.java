package fr.pacifista.api.service.permissions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPermissionRepository extends ApiRepository<PacifistaPermission> {
}
