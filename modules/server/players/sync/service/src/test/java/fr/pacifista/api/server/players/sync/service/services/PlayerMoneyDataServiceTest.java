package fr.pacifista.api.server.players.sync.service.services;

import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PlayerMoneyDataServiceTest {

    @Autowired
    PlayerMoneyDataService playerMoneyDataService;

    @Test
    void createEntity() {
        final PlayerMoneyDataDTO moneyDataDTO = new PlayerMoneyDataDTO();
        moneyDataDTO.setMoney(100.0);
        moneyDataDTO.setOfflineMoney(100.0);
        moneyDataDTO.setPlayerOwnerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final PlayerMoneyDataDTO res = playerMoneyDataService.create(moneyDataDTO);
            assertEquals(moneyDataDTO.getMoney(), res.getMoney());
            assertEquals(moneyDataDTO.getPlayerOwnerUuid(), res.getPlayerOwnerUuid());
        });
    }

    @Test
    void patchEntity() {
        final PlayerMoneyDataDTO moneyDataDTO = new PlayerMoneyDataDTO();
        moneyDataDTO.setMoney(100.0);
        moneyDataDTO.setOfflineMoney(100.0);
        moneyDataDTO.setPlayerOwnerUuid(UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final PlayerMoneyDataDTO res = playerMoneyDataService.create(moneyDataDTO);
            res.setMoney(200.0);

            final PlayerMoneyDataDTO patched = playerMoneyDataService.update(res);
            assertEquals(res.getMoney(), patched.getMoney());
        });
    }

}
