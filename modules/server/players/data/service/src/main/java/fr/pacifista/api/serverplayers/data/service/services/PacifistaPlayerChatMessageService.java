package fr.pacifista.api.serverplayers.data.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerChatMessageDTO;
import fr.pacifista.api.serverplayers.data.service.entities.PacifistaPlayerChatMessage;
import fr.pacifista.api.serverplayers.data.service.mappers.PacifistaPlayerChatMessageMapper;
import fr.pacifista.api.serverplayers.data.service.repositories.PacifistaPlayerChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlayerChatMessageService extends ApiService<PacifistaPlayerChatMessageDTO, PacifistaPlayerChatMessage, PacifistaPlayerChatMessageMapper, PacifistaPlayerChatMessageRepository> {

    public PacifistaPlayerChatMessageService(PacifistaPlayerChatMessageRepository repository,
                                             PacifistaPlayerChatMessageMapper mapper) {
        super(repository, mapper);
    }

}
