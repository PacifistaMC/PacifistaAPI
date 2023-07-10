package fr.pacifista.api.server.permissions.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPlayerRole;
import fr.pacifista.api.server.permissions.service.entities.PacifistaRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacifistaPlayerRoleRepository extends ApiRepository<PacifistaPlayerRole> {
    List<PacifistaPlayerRole> findPacifistaPlayerRolesByRole(PacifistaRole role);
    List<PacifistaPlayerRole> findPacifistaPlayerRolesByPlayerUuid(String playerUuid);
}
