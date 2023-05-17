package fr.pacifista.api.service.server.players.players_sync.services;

import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerEnderchestDataDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PlayerEnderchestDataServiceTest {

    @Autowired
    PlayerEnderchestDataService playerEnderchestDataService;

    @Test
    void createEntity() {
        final PlayerEnderchestDataDTO enderchestDataDTO = new PlayerEnderchestDataDTO();
        enderchestDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        enderchestDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        enderchestDataDTO.setEnderchestSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestPaladinSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestEliteSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestLegendaireSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestMineSerialized(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final PlayerEnderchestDataDTO res = playerEnderchestDataService.create(enderchestDataDTO);
            assertEquals(enderchestDataDTO.getPlayerOwnerUuid(), res.getPlayerOwnerUuid());
            assertEquals(enderchestDataDTO.getGameMode(), res.getGameMode());
            assertEquals(enderchestDataDTO.getEnderchestSerialized(), res.getEnderchestSerialized());
            assertEquals(enderchestDataDTO.getEnderchestPaladinSerialized(), res.getEnderchestPaladinSerialized());
            assertEquals(enderchestDataDTO.getEnderchestEliteSerialized(), res.getEnderchestEliteSerialized());
            assertEquals(enderchestDataDTO.getEnderchestLegendaireSerialized(), res.getEnderchestLegendaireSerialized());
            assertEquals(enderchestDataDTO.getEnderchestMineSerialized(), res.getEnderchestMineSerialized());
        });
    }

    @Test
    void patchEntity() {
        final PlayerEnderchestDataDTO enderchestDataDTO = new PlayerEnderchestDataDTO();
        enderchestDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        enderchestDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        enderchestDataDTO.setEnderchestSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestPaladinSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestEliteSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestLegendaireSerialized(UUID.randomUUID().toString());
        enderchestDataDTO.setEnderchestMineSerialized(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final PlayerEnderchestDataDTO res = playerEnderchestDataService.create(enderchestDataDTO);
            res.setEnderchestSerialized(UUID.randomUUID().toString());
            res.setEnderchestPaladinSerialized(UUID.randomUUID().toString());
            res.setEnderchestEliteSerialized(UUID.randomUUID().toString());
            res.setEnderchestLegendaireSerialized(UUID.randomUUID().toString());
            res.setEnderchestMineSerialized(UUID.randomUUID().toString());

            final PlayerEnderchestDataDTO patched = playerEnderchestDataService.update(res);
            assertEquals(res.getPlayerOwnerUuid(), patched.getPlayerOwnerUuid());
            assertEquals(res.getGameMode(), patched.getGameMode());
            assertEquals(res.getEnderchestSerialized(), patched.getEnderchestSerialized());
            assertEquals(res.getEnderchestPaladinSerialized(), patched.getEnderchestPaladinSerialized());
            assertEquals(res.getEnderchestEliteSerialized(), patched.getEnderchestEliteSerialized());
            assertEquals(res.getEnderchestLegendaireSerialized(), patched.getEnderchestLegendaireSerialized());
            assertEquals(res.getEnderchestMineSerialized(), patched.getEnderchestMineSerialized());
        });
    }

}
