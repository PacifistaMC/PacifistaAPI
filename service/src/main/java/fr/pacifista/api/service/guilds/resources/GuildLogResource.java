package fr.pacifista.api.service.guilds.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.guilds.dtos.GuildLogDTO;
import fr.pacifista.api.service.guilds.services.GuildLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/logs")
public class GuildLogResource extends ApiResource<GuildLogDTO, GuildLogService> {
    public GuildLogResource(GuildLogService guildLogService) {
        super(guildLogService);
    }
}
