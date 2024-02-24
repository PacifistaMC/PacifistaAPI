package fr.pacifista.api.server.players.data.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerChatMessageClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerChatMessageDTO;
import fr.pacifista.api.server.players.data.service.services.PacifistaPlayerChatMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playerdata/chatmessages")
public class PacifistaPlayerChatMessageResource extends ApiResource<PacifistaPlayerChatMessageDTO, PacifistaPlayerChatMessageService> implements PacifistaPlayerChatMessageClient {

    public PacifistaPlayerChatMessageResource(PacifistaPlayerChatMessageService pacifistaPlayerChatMessageService) {
        super(pacifistaPlayerChatMessageService);
    }

}
