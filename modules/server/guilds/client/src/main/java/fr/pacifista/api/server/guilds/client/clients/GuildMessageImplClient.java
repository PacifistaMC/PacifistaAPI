package fr.pacifista.api.server.guilds.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildMessageDTO;

public class GuildMessageImplClient extends FeignImpl<GuildMessageDTO, GuildMessageClient> implements GuildMessageClient {
    public GuildMessageImplClient() {
        super("guilds/messages", GuildMessageClient.class);
    }
}
