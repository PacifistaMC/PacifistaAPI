package fr.pacifista.api.service.permissions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacifistaPlayerRoleRepository extends ApiRepository<PacifistaPlayerRole> {
    List<PacifistaPlayerRole> findPacifistaPlayerRolesByRole(PacifistaRole role);
    List<PacifistaPlayerRole> findPacifistaPlayerRolesByStaffRole(PacifistaRole role);
}
