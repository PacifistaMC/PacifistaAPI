package fr.pacifista.api.service.server.guilds.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.guilds.dtos.GuildMessageDTO;
import fr.pacifista.api.service.server.guilds.entities.Guild;
import fr.pacifista.api.service.server.guilds.entities.GuildMessage;
import fr.pacifista.api.service.server.guilds.mappers.GuildMessageMapper;
import fr.pacifista.api.service.server.guilds.repositories.GuildMessageRepository;
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
    public void afterMapperCall(@NonNull GuildMessageDTO dto, @NonNull GuildMessage entity) {
        if (dto.getId() == null) {
            final Guild guild = guildService.findGuildById(dto.getGuildId());
            entity.setGuild(guild);
        }
    }

}
