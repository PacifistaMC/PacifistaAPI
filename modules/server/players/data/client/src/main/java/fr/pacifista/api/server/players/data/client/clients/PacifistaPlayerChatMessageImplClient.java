package fr.pacifista.api.server.players.data.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerChatMessageDTO;

public class PacifistaPlayerChatMessageImplClient extends FeignImpl<PacifistaPlayerChatMessageDTO, PacifistaPlayerChatMessageClient> implements PacifistaPlayerChatMessageClient {
    public PacifistaPlayerChatMessageImplClient() {
        super("playerdata/chatmessages", PacifistaPlayerChatMessageClient.class);
    }
}
