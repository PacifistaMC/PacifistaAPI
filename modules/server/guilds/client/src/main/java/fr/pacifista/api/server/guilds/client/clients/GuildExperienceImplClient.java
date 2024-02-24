package fr.pacifista.api.server.guilds.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.guilds.client.dtos.GuildExperienceDTO;

public class GuildExperienceImplClient extends FeignImpl<GuildExperienceDTO, GuildExperienceClient> implements GuildExperienceClient {
    public GuildExperienceImplClient() {
        super("guilds/exp", GuildExperienceClient.class);
    }
}
