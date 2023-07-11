package fr.pacifista.api.server.guilds.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.guilds.client.dtos.GuildMessageDTO;
import fr.pacifista.api.server.guilds.service.services.GuildMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/messages")
public class GuildMessageResource extends ApiResource<GuildMessageDTO, GuildMessageService> {
    public GuildMessageResource(GuildMessageService guildMessageService) {
        super(guildMessageService);
    }
}
