package fr.pacifista.api.server.guilds.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import fr.pacifista.api.server.guilds.service.services.GuildService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds")
public class GuildResource extends ApiResource<GuildDTO, GuildService> {
    public GuildResource(GuildService guildService) {
        super(guildService);
    }
}
