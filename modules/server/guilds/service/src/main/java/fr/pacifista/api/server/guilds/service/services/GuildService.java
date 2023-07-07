package fr.pacifista.api.server.guilds.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import fr.pacifista.api.server.guilds.service.entities.GuildExperience;
import fr.pacifista.api.server.guilds.service.mappers.GuildMapper;
import fr.pacifista.api.server.guilds.service.repositories.GuildExperienceRepository;
import fr.pacifista.api.server.guilds.service.repositories.GuildRepository;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuildService extends ApiService<GuildDTO, Guild, GuildMapper, GuildRepository> {

    private final GuildExperienceRepository guildExperienceRepository;

    public GuildService(GuildRepository repository,
                        GuildMapper mapper,
                        GuildExperienceRepository guildExperienceRepository) {
        super(repository, mapper);
        this.guildExperienceRepository = guildExperienceRepository;
    }

    @Override
    public void afterSavingEntity(@NonNull Iterable<Guild> entity) {
        for (final Guild guild : entity) {
            if (guild.getExperience() == null) {
                GuildExperience guildExperience = new GuildExperience();
                guildExperience.setGuild(guild);
                guildExperience.setLevel(0);
                guildExperience.setExperience(0);

                guildExperience = this.guildExperienceRepository.save(guildExperience);
                guild.setExperience(guildExperience);
            }
        }
    }

    protected Guild findGuildById(@Nullable UUID id) throws ApiBadRequestException {
        if (id == null) {
            throw new ApiBadRequestException("Vous n'avez pas spécifié d'id de guilde.");
        } else {
            return getRepository().findByUuid(
                    id.toString()
            ).orElseThrow(() -> new ApiNotFoundException("La guilde id " + id + " n'existe pas."));
        }
    }
}
