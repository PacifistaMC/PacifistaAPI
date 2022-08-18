package fr.pacifista.api.service.permissions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacifistaRoleHeritageRepository extends ApiRepository<PacifistaRoleHeritage> {

    List<PacifistaRoleHeritage> findAllByRole(PacifistaRole role);
    List<PacifistaRoleHeritage> findAllByHeritage(PacifistaRole role);

}
