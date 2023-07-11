package fr.pacifista.api.server.guilds.service.services;

import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

abstract class GuildServiceTestHandler {

    @Autowired
    GuildService guildService;

    protected GuildDTO generateGuild() {
        final GuildDTO guildDTO = new GuildDTO();

        guildDTO.setName(UUID.randomUUID().toString());
        guildDTO.setDescription(UUID.randomUUID().toString());
        guildDTO.setMoney(100.9);
        return guildService.create(guildDTO);
    }

}
