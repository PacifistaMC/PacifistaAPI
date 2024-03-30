package fr.pacifista.api.server.claim.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.claim.service.entities.ClaimDataConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimDataConfigRepository extends ApiRepository<ClaimDataConfig> {
}
