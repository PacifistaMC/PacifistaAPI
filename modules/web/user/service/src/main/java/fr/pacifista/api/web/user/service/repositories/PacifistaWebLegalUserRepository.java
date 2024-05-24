package fr.pacifista.api.web.user.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.user.service.entities.PacifistaWebLegalUser;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaWebLegalUserRepository extends ApiRepository<PacifistaWebLegalUser> {
}
