package fr.pacifista.api.server.guilds.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.guilds.client.dtos.GuildHomeDTO;
import fr.pacifista.api.server.guilds.service.services.GuildHomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/homes")
public class GuildHomeResource extends ApiResource<GuildHomeDTO, GuildHomeService> {
    public GuildHomeResource(GuildHomeService guildHomeService) {
        super(guildHomeService);
    }
}
