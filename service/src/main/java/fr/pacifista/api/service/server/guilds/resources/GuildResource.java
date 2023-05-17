package fr.pacifista.api.service.server.guilds.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.guilds.dtos.GuildDTO;
import fr.pacifista.api.service.server.guilds.services.GuildService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds")
public class GuildResource extends ApiResource<GuildDTO, GuildService> {
    public GuildResource(GuildService guildService) {
        super(guildService);
    }
}
