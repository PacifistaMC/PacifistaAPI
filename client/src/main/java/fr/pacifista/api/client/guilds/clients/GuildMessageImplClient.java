package fr.pacifista.api.client.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.guilds.dtos.GuildMessageDTO;

public class GuildMessageImplClient extends FeignImpl<GuildMessageDTO, GuildMessageClient> implements GuildMessageClient {
    public GuildMessageImplClient() {
        super("guilds/messages", GuildMessageClient.class);
    }
}
