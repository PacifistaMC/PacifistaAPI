package fr.pacifista.api.service.server.guilds.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.server.guilds.entities.GuildMember;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildMemberRepository extends ApiRepository<GuildMember> {
}
