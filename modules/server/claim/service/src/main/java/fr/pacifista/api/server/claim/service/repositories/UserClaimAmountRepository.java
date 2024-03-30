package fr.pacifista.api.server.claim.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.claim.service.entities.UserClaimAmount;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClaimAmountRepository extends ApiRepository<UserClaimAmount> {
}
