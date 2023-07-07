package fr.pacifista.api.server.guilds.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.guilds.client.dtos.GuildHomeDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import fr.pacifista.api.server.guilds.service.entities.GuildHome;
import fr.pacifista.api.server.guilds.service.mappers.GuildHomeMapper;
import fr.pacifista.api.server.guilds.service.repositories.GuildHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GuildHomeService extends ApiService<GuildHomeDTO, GuildHome, GuildHomeMapper, GuildHomeRepository> {

    private final GuildService guildService;

    public GuildHomeService(GuildHomeRepository repository,
                            GuildService guildService,
                            GuildHomeMapper mapper) {
        super(repository, mapper);
        this.guildService = guildService;
    }

    @Override
    public void afterMapperCall(@NonNull GuildHomeDTO dto, @NonNull GuildHome entity) {
        if (dto.getId() == null) {
            final Guild guild = guildService.findGuildById(dto.getGuildId());
            entity.setGuild(guild);
        }
    }

}
