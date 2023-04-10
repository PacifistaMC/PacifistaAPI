package fr.pacifista.api.service.guilds.services;

import fr.pacifista.api.client.guilds.dtos.GuildDTO;
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
