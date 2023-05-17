package fr.pacifista.api.service.server.guilds.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.guilds.dtos.GuildExperienceDTO;
import fr.pacifista.api.service.server.guilds.services.GuildExperienceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/exp")
public class GuildExperienceResource extends ApiResource<GuildExperienceDTO, GuildExperienceService> {
    public GuildExperienceResource(GuildExperienceService guildExperienceService) {
        super(guildExperienceService);
    }
}
