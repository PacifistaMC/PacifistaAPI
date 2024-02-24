package fr.pacifista.api.server.players.data.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerSession;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPlayerSessionRepository extends ApiRepository<PacifistaPlayerSession> {
}
