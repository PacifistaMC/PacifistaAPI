package fr.pacifista.api.client.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;

public class GuildMemberImplClient extends FeignImpl<GuildMemberDTO, GuildMemberClient> implements GuildMemberClient {
    public GuildMemberImplClient() {
        super("guilds/members", GuildMemberClient.class);
    }
}
