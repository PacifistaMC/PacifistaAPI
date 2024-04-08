package fr.pacifista.api.server.sanctions.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.sanctions.service.entities.Sanction;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionRepository extends ApiRepository<Sanction> {
}
