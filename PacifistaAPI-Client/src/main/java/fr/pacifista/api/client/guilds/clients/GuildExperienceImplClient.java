package fr.pacifista.api.client.guilds.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.guilds.dtos.GuildExperienceDTO;

public class GuildExperienceImplClient extends FeignImpl<GuildExperienceDTO, GuildExperienceClient> implements GuildExperienceClient {
    public GuildExperienceImplClient() {
        super("guilds/exp", GuildExperienceClient.class);
    }
}
