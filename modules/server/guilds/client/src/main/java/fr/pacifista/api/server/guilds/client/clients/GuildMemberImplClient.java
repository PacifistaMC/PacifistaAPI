package fr.pacifista.api.server.guilds.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildMemberDTO;

public class GuildMemberImplClient extends FeignImpl<GuildMemberDTO, GuildMemberClient> implements GuildMemberClient {
    public GuildMemberImplClient() {
        super("guilds/members", GuildMemberClient.class);
    }
}
