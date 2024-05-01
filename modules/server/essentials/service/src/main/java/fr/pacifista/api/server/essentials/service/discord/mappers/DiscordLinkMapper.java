package fr.pacifista.api.server.essentials.service.discord.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
import fr.pacifista.api.server.essentials.service.discord.entities.DiscordLink;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscordLinkMapper extends ApiMapper<DiscordLink, DiscordLinkDTO> {
}
