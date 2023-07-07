package fr.pacifista.api.server.guilds.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.guilds.client.dtos.GuildExperienceDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import fr.pacifista.api.server.guilds.service.entities.GuildExperience;
import fr.pacifista.api.server.guilds.service.mappers.GuildExperienceMapper;
import fr.pacifista.api.server.guilds.service.repositories.GuildExperienceRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GuildExperienceService extends ApiService<GuildExperienceDTO, GuildExperience, GuildExperienceMapper, GuildExperienceRepository> {

    private final GuildService guildService;

    public GuildExperienceService(GuildExperienceRepository repository,
                                  GuildExperienceMapper mapper,
                                  GuildService guildService) {
        super(repository, mapper);
        this.guildService = guildService;
    }

    @Override
    public void afterMapperCall(@NonNull GuildExperienceDTO dto, @NonNull GuildExperience entity) {
        if (dto.getId() == null) {
            final Guild guild = guildService.findGuildById(dto.getGuildId());
            entity.setGuild(guild);
        }
    }

}
