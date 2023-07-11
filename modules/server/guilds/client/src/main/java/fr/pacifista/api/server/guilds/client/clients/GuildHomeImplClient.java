package fr.pacifista.api.server.guilds.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildHomeDTO;

public class GuildHomeImplClient extends FeignImpl<GuildHomeDTO, GuildHomeClient> implements GuildHomeClient {
    public GuildHomeImplClient() {
        super("guilds/homes", GuildHomeClient.class);
    }
}
