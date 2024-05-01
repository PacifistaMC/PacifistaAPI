package fr.pacifista.api.server.essentials.service.discord.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.discord.entities.DiscordLink;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordLinkRepository extends ApiRepository<DiscordLink> {
}
