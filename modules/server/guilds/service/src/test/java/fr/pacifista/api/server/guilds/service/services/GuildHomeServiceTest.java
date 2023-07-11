package fr.pacifista.api.server.guilds.service.services;

import fr.pacifista.api.core.client.enums.enums.ServerType;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import fr.pacifista.api.server.guilds.client.dtos.GuildHomeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GuildHomeServiceTest extends GuildServiceTestHandler {

    @Autowired
    GuildHomeService guildHomeService;

    @Test
    void createEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildHomeDTO homeDTO = new GuildHomeDTO();

        homeDTO.setGuildId(guildDTO.getId());
        homeDTO.setPublicAccess(true);
        homeDTO.setWorldUuid(UUID.randomUUID());
        homeDTO.setX(0d);
        homeDTO.setY(0d);
        homeDTO.setZ(0d);
        homeDTO.setYaw(0f);
        homeDTO.setPitch(0f);
        homeDTO.setServerType(ServerType.CREATIVE);

        assertDoesNotThrow(() -> {
            final GuildHomeDTO created = guildHomeService.create(homeDTO);
            assertEquals(homeDTO.getGuildId(), created.getGuildId());
            assertEquals(homeDTO.getPublicAccess(), created.getPublicAccess());
            assertEquals(homeDTO.getWorldUuid(), created.getWorldUuid());
            assertEquals(homeDTO.getX(), created.getX());
            assertEquals(homeDTO.getY(), created.getY());
            assertEquals(homeDTO.getZ(), created.getZ());
            assertEquals(homeDTO.getYaw(), created.getYaw());
            assertEquals(homeDTO.getPitch(), created.getPitch());
            assertEquals(homeDTO.getServerType(), created.getServerType());
        });
    }

    @Test
    void patchEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildHomeDTO homeDTO = new GuildHomeDTO();

        homeDTO.setGuildId(guildDTO.getId());
        homeDTO.setPublicAccess(true);
        homeDTO.setWorldUuid(UUID.randomUUID());
        homeDTO.setX(0d);
        homeDTO.setY(0d);
        homeDTO.setZ(0d);
        homeDTO.setYaw(0f);
        homeDTO.setPitch(0f);
        homeDTO.setServerType(ServerType.CREATIVE);

        assertDoesNotThrow(() -> {
            final GuildHomeDTO created = guildHomeService.create(homeDTO);
            created.setPublicAccess(false);
            final GuildHomeDTO patched = guildHomeService.update(created);
            assertEquals(created.getGuildId(), patched.getGuildId());
            assertEquals(created.getPublicAccess(), patched.getPublicAccess());
            assertEquals(created.getWorldUuid(), patched.getWorldUuid());
            assertEquals(created.getX(), patched.getX());
            assertEquals(created.getY(), patched.getY());
            assertEquals(created.getZ(), patched.getZ());
            assertEquals(created.getYaw(), patched.getYaw());
            assertEquals(created.getPitch(), patched.getPitch());
            assertEquals(created.getServerType(), patched.getServerType());
        });
    }

}
