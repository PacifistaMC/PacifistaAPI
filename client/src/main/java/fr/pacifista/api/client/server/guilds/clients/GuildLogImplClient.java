package fr.pacifista.api.client.server.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.guilds.dtos.GuildLogDTO;

public class GuildLogImplClient extends FeignImpl<GuildLogDTO, GuildLogClient> implements GuildLogClient {
    public GuildLogImplClient() {
        super("guilds/logs", GuildLogClient.class);
    }
}
