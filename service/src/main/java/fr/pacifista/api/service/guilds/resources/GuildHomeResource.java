package fr.pacifista.api.service.guilds.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.guilds.dtos.GuildHomeDTO;
import fr.pacifista.api.service.guilds.services.GuildHomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/homes")
public class GuildHomeResource extends ApiResource<GuildHomeDTO, GuildHomeService> {
    public GuildHomeResource(GuildHomeService guildHomeService) {
        super(guildHomeService);
    }
}
