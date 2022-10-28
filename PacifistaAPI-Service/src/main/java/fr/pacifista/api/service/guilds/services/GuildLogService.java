package fr.pacifista.api.service.guilds.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.guilds.dtos.GuildLogDTO;
import fr.pacifista.api.service.guilds.entities.Guild;
import fr.pacifista.api.service.guilds.entities.GuildLog;
import fr.pacifista.api.service.guilds.mappers.GuildLogMapper;
import fr.pacifista.api.service.guilds.repositories.GuildLogRepository;
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
    public void beforeSavingEntity(@NonNull GuildLogDTO request, @NonNull GuildLog entity) {
        if (request.getId() != null) {
            final Guild guild = guildService.findGuildById(request.getGuildId());
            entity.setGuild(guild);
        }
    }

}
