package fr.pacifista.api.client.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.guilds.dtos.GuildHomeDTO;

public class GuildHomeImplClient extends FeignImpl<GuildHomeDTO, GuildHomeClient> implements GuildHomeClient {
    public GuildHomeImplClient() {
        super("guilds/homes", GuildHomeClient.class);
    }
}
