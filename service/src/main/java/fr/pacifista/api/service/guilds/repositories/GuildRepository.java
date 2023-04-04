package fr.pacifista.api.service.guilds.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.guilds.entities.Guild;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends ApiRepository<Guild> {
}
