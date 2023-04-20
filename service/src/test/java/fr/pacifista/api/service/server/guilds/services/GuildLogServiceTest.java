package fr.pacifista.api.service.guilds.services;

import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.client.guilds.dtos.GuildLogDTO;
import fr.pacifista.api.service.server.guilds.services.GuildLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GuildLogServiceTest extends GuildServiceTestHandler {

    @Autowired
    GuildLogService guildLogService;

    @Test
    void createEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildLogDTO logDTO = new GuildLogDTO();
        logDTO.setGuildId(guildDTO.getId());
        logDTO.setAction("hey");
        logDTO.setPlayerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final GuildLogDTO created = guildLogService.create(logDTO);
            assertEquals(logDTO.getAction(), created.getAction());
            assertEquals(logDTO.getPlayerUuid(), created.getPlayerUuid());
            assertEquals(logDTO.getGuildId(), created.getGuildId());
        });
    }

    @Test
    void patchEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildLogDTO logDTO = new GuildLogDTO();
        logDTO.setGuildId(guildDTO.getId());
        logDTO.setAction("hey");
        logDTO.setPlayerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final GuildLogDTO created = guildLogService.create(logDTO);
            created.setAction("hey2");
            final GuildLogDTO patched = guildLogService.update(created);
            assertEquals(created.getAction(), patched.getAction());
            assertEquals(created.getPlayerUuid(), patched.getPlayerUuid());
            assertEquals(created.getGuildId(), patched.getGuildId());
        });
    }

}
