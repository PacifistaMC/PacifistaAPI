package fr.pacifista.api.service.server.guilds.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.guilds.entities.GuildExperience;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildExperienceRepository extends ApiRepository<GuildExperience> {
}
