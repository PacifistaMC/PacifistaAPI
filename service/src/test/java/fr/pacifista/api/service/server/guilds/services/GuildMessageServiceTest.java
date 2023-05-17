package fr.pacifista.api.service.server.guilds.services;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.server.guilds.dtos.GuildDTO;
import fr.pacifista.api.client.server.guilds.dtos.GuildMessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuildMessageServiceTest extends GuildServiceTestHandler {

    @Autowired
    GuildMessageService service;

    @Test
    void testCreateEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildMessageDTO messageDTO = new GuildMessageDTO();
        messageDTO.setGuildId(guildDTO.getId());
        messageDTO.setPlayerUuid(UUID.randomUUID());
        messageDTO.setSubject(UUID.randomUUID().toString());
        messageDTO.setMessage(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final GuildMessageDTO response = this.service.create(messageDTO);
            assertEquals(messageDTO.getGuildId(), response.getGuildId());
            assertEquals(messageDTO.getPlayerUuid(), response.getPlayerUuid());
            assertEquals(messageDTO.getSubject(), response.getSubject());
            assertEquals(messageDTO.getMessage(), response.getMessage());
        });
    }

    @Test
    void testCreateEntityWithNoGuild() {
        final GuildMessageDTO messageDTO = new GuildMessageDTO();
        messageDTO.setPlayerUuid(UUID.randomUUID());
        messageDTO.setSubject(UUID.randomUUID().toString());
        messageDTO.setMessage(UUID.randomUUID().toString());

        assertThrowsExactly(ApiBadRequestException.class, () -> {
            this.service.create(messageDTO);
        });
    }

    @Test
    void testCreateEntityWithGuildNotFound() {
        final GuildMessageDTO messageDTO = new GuildMessageDTO();
        messageDTO.setGuildId(UUID.randomUUID());
        messageDTO.setPlayerUuid(UUID.randomUUID());
        messageDTO.setSubject(UUID.randomUUID().toString());
        messageDTO.setMessage(UUID.randomUUID().toString());

        assertThrowsExactly(ApiNotFoundException.class, () -> {
            this.service.create(messageDTO);
        });
    }

    @Test
    void testPatchEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildMessageDTO messageDTO = new GuildMessageDTO();
        messageDTO.setGuildId(guildDTO.getId());
        messageDTO.setPlayerUuid(UUID.randomUUID());
        messageDTO.setSubject(UUID.randomUUID().toString());
        messageDTO.setMessage(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final GuildMessageDTO response = this.service.create(messageDTO);
            response.setMessage("deux");

            final GuildMessageDTO patched = this.service.update(response);
            assertEquals(response.getMessage(), patched.getMessage());
        });
    }

}
