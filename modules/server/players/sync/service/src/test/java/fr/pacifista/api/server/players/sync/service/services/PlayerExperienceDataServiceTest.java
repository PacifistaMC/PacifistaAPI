package fr.pacifista.api.server.players.sync.service.services;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerExperienceDataServiceTest {

    @Autowired
    PlayerExperienceDataService playerExperienceDataService;

    @Test
    void createEntity() {
        final PlayerExperienceDataDTO experienceDataDTO = new PlayerExperienceDataDTO();
        experienceDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        experienceDataDTO.setTotalExperience(100);
        experienceDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        experienceDataDTO.setExp(0.4f);
        experienceDataDTO.setLevel(10);

        assertDoesNotThrow(() -> {
            final PlayerExperienceDataDTO res = playerExperienceDataService.create(experienceDataDTO);
            assertEquals(experienceDataDTO.getPlayerOwnerUuid(), res.getPlayerOwnerUuid());
            assertEquals(experienceDataDTO.getTotalExperience(), res.getTotalExperience());
            assertEquals(experienceDataDTO.getGameMode(), res.getGameMode());
            assertEquals(experienceDataDTO.getExp(), res.getExp());
            assertEquals(experienceDataDTO.getLevel(), res.getLevel());
        });
    }

    @Test
    void patchEntity() {
        final PlayerExperienceDataDTO experienceDataDTO = new PlayerExperienceDataDTO();
        experienceDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        experienceDataDTO.setTotalExperience(100);
        experienceDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        experienceDataDTO.setExp(0.4f);
        experienceDataDTO.setLevel(10);

        assertDoesNotThrow(() -> {
            final PlayerExperienceDataDTO res = playerExperienceDataService.create(experienceDataDTO);
            res.setTotalExperience(200);
            res.setGameMode(ServerGameMode.CREATIVE);
            res.setExp(0.5f);
            res.setLevel(20);

            final PlayerExperienceDataDTO patched = playerExperienceDataService.update(res);
            assertEquals(res.getPlayerOwnerUuid(), patched.getPlayerOwnerUuid());
            assertEquals(res.getTotalExperience(), patched.getTotalExperience());
            assertEquals(res.getGameMode(), patched.getGameMode());
            assertEquals(res.getExp(), patched.getExp());
            assertEquals(res.getLevel(), patched.getLevel());
        });
    }

    @Test
    void testCreateReplica() {
        final PlayerExperienceDataDTO experienceDataDTO = new PlayerExperienceDataDTO();
        experienceDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        experienceDataDTO.setTotalExperience(100);
        experienceDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        experienceDataDTO.setExp(0.4f);
        experienceDataDTO.setLevel(10);

        final PlayerExperienceDataDTO res = playerExperienceDataService.create(experienceDataDTO);
        assertThrowsExactly(ApiBadRequestException.class, () -> playerExperienceDataService.create(res));

        experienceDataDTO.setGameMode(ServerGameMode.CREATIVE);
        assertDoesNotThrow(() -> playerExperienceDataService.create(experienceDataDTO));
    }

}
