package fr.pacifista.api.service.guilds.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.guilds.dtos.GuildMessageDTO;
import fr.pacifista.api.service.guilds.services.GuildMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/messages")
public class GuildMessageResource extends ApiResource<GuildMessageDTO, GuildMessageService> {
    public GuildMessageResource(GuildMessageService guildMessageService) {
        super(guildMessageService);
    }
}
