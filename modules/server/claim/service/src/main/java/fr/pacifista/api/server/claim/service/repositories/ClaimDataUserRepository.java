package fr.pacifista.api.server.claim.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.claim.service.entities.ClaimDataUser;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimDataUserRepository extends ApiRepository<ClaimDataUser> {
}
