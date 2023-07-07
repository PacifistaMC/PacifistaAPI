package fr.pacifista.api.server.guilds.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends ApiRepository<Guild> {
}
