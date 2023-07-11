package fr.pacifista.api.server.guilds.service.services;

import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import fr.pacifista.api.server.guilds.client.dtos.GuildExperienceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GuildExperienceServiceTest extends GuildServiceTestHandler {

    @Autowired
    GuildExperienceService guildExperienceService;

    @Test
    void patchEntity() {
        final GuildDTO guildDTO = super.generateGuild();

        assertDoesNotThrow(() -> {
            final GuildExperienceDTO created = guildDTO.getExperience();
            created.setExperience(200);
            created.setLevel(200);
            final GuildExperienceDTO patched = guildExperienceService.update(created);
            assertEquals(created.getExperience(), patched.getExperience());
            assertEquals(created.getLevel(), patched.getLevel());
            assertEquals(created.getGuildId(), patched.getGuildId());
        });
    }

}
