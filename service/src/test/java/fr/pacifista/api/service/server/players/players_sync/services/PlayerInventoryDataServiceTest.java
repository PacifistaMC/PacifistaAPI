package fr.pacifista.api.service.server.players.players_sync.services;

import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerInventoryDataDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PlayerInventoryDataServiceTest {

    @Autowired
    PlayerInventoryDataService playerInventoryDataService;

    @Test
    void createEntity() {
        final PlayerInventoryDataDTO inventoryDataDTO = new PlayerInventoryDataDTO();
        inventoryDataDTO.setInventory("inventory");
        inventoryDataDTO.setArmor("armor");
        inventoryDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        inventoryDataDTO.setPlayerOwnerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final PlayerInventoryDataDTO res = playerInventoryDataService.create(inventoryDataDTO);
            assertEquals(inventoryDataDTO.getInventory(), res.getInventory());
            assertEquals(inventoryDataDTO.getArmor(), res.getArmor());
            assertEquals(inventoryDataDTO.getGameMode(), res.getGameMode());
            assertEquals(inventoryDataDTO.getPlayerOwnerUuid(), res.getPlayerOwnerUuid());
        });
    }

    @Test
    void patchEntity() {
        final PlayerInventoryDataDTO inventoryDataDTO = new PlayerInventoryDataDTO();
        inventoryDataDTO.setInventory("inventory");
        inventoryDataDTO.setArmor("armor");
        inventoryDataDTO.setGameMode(ServerGameMode.SKYBLOCK);
        inventoryDataDTO.setPlayerOwnerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final PlayerInventoryDataDTO res = playerInventoryDataService.create(inventoryDataDTO);
            res.setInventory("new inventory");
            res.setArmor("new armor");
            res.setGameMode(ServerGameMode.CREATIVE);

            final PlayerInventoryDataDTO patched = playerInventoryDataService.update(res);
            assertEquals(res.getInventory(), patched.getInventory());
            assertEquals(res.getArmor(), patched.getArmor());
            assertEquals(res.getGameMode(), patched.getGameMode());
        });
    }

}
