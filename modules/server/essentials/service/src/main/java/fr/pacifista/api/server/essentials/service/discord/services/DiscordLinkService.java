package fr.pacifista.api.server.essentials.service.discord.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
import fr.pacifista.api.server.essentials.service.discord.entities.DiscordLink;
import fr.pacifista.api.server.essentials.service.discord.mappers.DiscordLinkMapper;
import fr.pacifista.api.server.essentials.service.discord.repositories.DiscordLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscordLinkService extends ApiService<DiscordLinkDTO, DiscordLink, DiscordLinkMapper, DiscordLinkRepository> {

    public DiscordLinkService(DiscordLinkRepository repository,
                              DiscordLinkMapper mapper) {
        super(repository, mapper);
    }

}
