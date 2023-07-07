package fr.pacifista.api.server.guilds.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildLogDTO;

public class GuildLogImplClient extends FeignImpl<GuildLogDTO, GuildLogClient> implements GuildLogClient {
    public GuildLogImplClient() {
        super("guilds/logs", GuildLogClient.class);
    }
}
