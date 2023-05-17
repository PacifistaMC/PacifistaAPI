package fr.pacifista.api.service.server.permissions.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.permissions.entities.PacifistaPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPermissionRepository extends ApiRepository<PacifistaPermission> {
}
