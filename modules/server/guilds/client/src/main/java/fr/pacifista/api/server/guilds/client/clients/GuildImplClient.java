package fr.pacifista.api.server.guilds.client.clients;


import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;

public class GuildImplClient extends FeignImpl<GuildDTO, GuildClient> implements GuildClient {
    public GuildImplClient() {
        super("guilds", GuildClient.class);
    }
}
