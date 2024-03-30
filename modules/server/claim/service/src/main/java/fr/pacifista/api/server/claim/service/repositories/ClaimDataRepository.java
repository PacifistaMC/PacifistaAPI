package fr.pacifista.api.server.claim.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimDataRepository extends ApiRepository<ClaimData> {
}
