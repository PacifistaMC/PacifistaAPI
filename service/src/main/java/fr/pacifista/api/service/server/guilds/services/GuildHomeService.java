package fr.pacifista.api.service.server.guilds.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.guilds.dtos.GuildHomeDTO;
import fr.pacifista.api.service.server.guilds.entities.Guild;
import fr.pacifista.api.service.server.guilds.entities.GuildHome;
import fr.pacifista.api.service.server.guilds.mappers.GuildHomeMapper;
import fr.pacifista.api.service.server.guilds.repositories.GuildHomeRepository;
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
