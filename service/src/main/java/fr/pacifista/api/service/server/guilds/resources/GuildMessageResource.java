package fr.pacifista.api.service.server.guilds.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.server.guilds.dtos.GuildMessageDTO;
import fr.pacifista.api.service.server.guilds.services.GuildMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/messages")
public class GuildMessageResource extends ApiResource<GuildMessageDTO, GuildMessageService> {
    public GuildMessageResource(GuildMessageService guildMessageService) {
        super(guildMessageService);
    }
}
