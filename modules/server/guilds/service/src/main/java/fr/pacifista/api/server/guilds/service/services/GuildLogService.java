package fr.pacifista.api.server.guilds.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.guilds.client.dtos.GuildLogDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import fr.pacifista.api.server.guilds.service.entities.GuildLog;
import fr.pacifista.api.server.guilds.service.mappers.GuildLogMapper;
import fr.pacifista.api.server.guilds.service.repositories.GuildLogRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GuildLogService extends ApiService<GuildLogDTO, GuildLog, GuildLogMapper, GuildLogRepository> {

    private final GuildService guildService;

    public GuildLogService(GuildLogRepository repository,
                           GuildService guildService,
                           GuildLogMapper mapper) {
        super(repository, mapper);
        this.guildService = guildService;
    }

    @Override
    public void afterMapperCall(@NonNull GuildLogDTO dto, @NonNull GuildLog entity) {
        if (dto.getId() == null) {
            final Guild guild = guildService.findGuildById(dto.getGuildId());
            entity.setGuild(guild);
        }
    }

}
