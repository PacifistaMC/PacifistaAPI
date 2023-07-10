package fr.pacifista.api.server.permissions.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPermissionRepository extends ApiRepository<PacifistaPermission> {
}
