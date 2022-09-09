package fr.pacifista.api.service.guilds.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.guilds.entities.GuildMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildMessageRepository extends ApiRepository<GuildMessage> {
}
