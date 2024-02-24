package fr.pacifista.api.server.players.data.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerChatMessageDTO;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerChatMessage;
import fr.pacifista.api.server.players.data.service.mappers.PacifistaPlayerChatMessageMapper;
import fr.pacifista.api.server.players.data.service.repositories.PacifistaPlayerChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlayerChatMessageService extends ApiService<PacifistaPlayerChatMessageDTO, PacifistaPlayerChatMessage, PacifistaPlayerChatMessageMapper, PacifistaPlayerChatMessageRepository> {

    public PacifistaPlayerChatMessageService(PacifistaPlayerChatMessageRepository repository,
                                             PacifistaPlayerChatMessageMapper mapper) {
        super(repository, mapper);
    }

}
