package fr.pacifista.api.client.server.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.guilds.dtos.GuildDTO;

public class GuildImplClient extends FeignImpl<GuildDTO, GuildClient> implements GuildClient {
    public GuildImplClient() {
        super("guilds", GuildClient.class);
    }
}
