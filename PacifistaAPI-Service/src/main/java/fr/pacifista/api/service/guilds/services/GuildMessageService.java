package fr.pacifista.api.service.guilds.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.guilds.dtos.GuildMessageDTO;
import fr.pacifista.api.service.guilds.entities.Guild;
import fr.pacifista.api.service.guilds.entities.GuildMessage;
import fr.pacifista.api.service.guilds.mappers.GuildMessageMapper;
import fr.pacifista.api.service.guilds.repositories.GuildMessageRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GuildMessageService extends ApiService<GuildMessageDTO, GuildMessage, GuildMessageMapper, GuildMessageRepository> {

    private final GuildService guildService;

    public GuildMessageService(GuildMessageRepository repository,
                               GuildService guildService,
                               GuildMessageMapper mapper) {
        super(repository, mapper);
        this.guildService = guildService;
    }

    @Override
    public void beforeSavingEntity(@NonNull GuildMessageDTO request, @NonNull GuildMessage entity) {
        if (request.getId() != null) {
            final Guild guild = guildService.findGuildById(request.getGuildId());
            entity.setGuild(guild);
        }
    }
}
