package fr.pacifista.api.service.server.guilds.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.guilds.entities.GuildLog;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildLogRepository extends ApiRepository<GuildLog> {
}
