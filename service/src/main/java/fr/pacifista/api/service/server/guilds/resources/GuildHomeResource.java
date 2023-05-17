package fr.pacifista.api.service.server.guilds.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.guilds.dtos.GuildHomeDTO;
import fr.pacifista.api.service.server.guilds.services.GuildHomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/homes")
public class GuildHomeResource extends ApiResource<GuildHomeDTO, GuildHomeService> {
    public GuildHomeResource(GuildHomeService guildHomeService) {
        super(guildHomeService);
    }
}
