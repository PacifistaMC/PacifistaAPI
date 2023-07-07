package fr.pacifista.api.server.guilds.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.guilds.client.dtos.GuildLogDTO;
import fr.pacifista.api.server.guilds.service.services.GuildLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/logs")
public class GuildLogResource extends ApiResource<GuildLogDTO, GuildLogService> {
    public GuildLogResource(GuildLogService guildLogService) {
        super(guildLogService);
    }
}
