package fr.pacifista.api.server.guilds.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.guilds.service.entities.GuildMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildMessageRepository extends ApiRepository<GuildMessage> {
}
