package fr.pacifista.api.service.guilds.services;

import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.client.guilds.dtos.GuildExperienceDTO;
import fr.pacifista.api.service.server.guilds.services.GuildExperienceService;
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
    void createEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildExperienceDTO experienceDTO = new GuildExperienceDTO();
        experienceDTO.setGuildId(guildDTO.getId());
        experienceDTO.setExperience(100);
        experienceDTO.setLevel(100);

        assertDoesNotThrow(() -> {
            final GuildExperienceDTO created = guildExperienceService.create(experienceDTO);
            assertEquals(experienceDTO.getExperience(), created.getExperience());
            assertEquals(experienceDTO.getLevel(), created.getLevel());
            assertEquals(experienceDTO.getGuildId(), created.getGuildId());
        });
    }

    @Test
    void patchEntity() {
        final GuildDTO guildDTO = super.generateGuild();
        final GuildExperienceDTO experienceDTO = new GuildExperienceDTO();
        experienceDTO.setGuildId(guildDTO.getId());
        experienceDTO.setExperience(100);
        experienceDTO.setLevel(100);

        assertDoesNotThrow(() -> {
            final GuildExperienceDTO created = guildExperienceService.create(experienceDTO);
            created.setExperience(200);
            created.setLevel(200);
            final GuildExperienceDTO patched = guildExperienceService.update(created);
            assertEquals(created.getExperience(), patched.getExperience());
            assertEquals(created.getLevel(), patched.getLevel());
            assertEquals(created.getGuildId(), patched.getGuildId());
        });
    }

}
