package fr.pacifista.api.server.players.data.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerChatMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaPlayerChatMessageRepository extends ApiRepository<PacifistaPlayerChatMessage> {
}
