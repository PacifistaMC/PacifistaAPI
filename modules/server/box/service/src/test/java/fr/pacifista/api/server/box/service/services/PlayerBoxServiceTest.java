package fr.pacifista.api.server.box.service.services;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerBoxServiceTest {

    @Autowired
    PlayerBoxService service;

    @Autowired
    BoxService boxService;

    @Test
    void testCreateEntity() {
        final BoxDTO boxDTO = generateBox();
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        playerBoxDTO.setBox(boxDTO);
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        assertDoesNotThrow(() -> {
            final PlayerBoxDTO res = this.service.create(playerBoxDTO);
            assertEquals(playerBoxDTO.getPlayerUuid(), res.getPlayerUuid());
            assertEquals(playerBoxDTO.getBox(), res.getBox());
            assertEquals(playerBoxDTO.getAmount(), res.getAmount());
        });
    }

    @Test
    void testCreateEntityWithNoBoxId() {
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        assertThrowsExactly(ApiBadRequestException.class, () -> service.create(playerBoxDTO));
    }

    @Test
    void testCreateEntityWithNoBoxId2() {
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        playerBoxDTO.setBox(new BoxDTO());
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        assertThrowsExactly(ApiBadRequestException.class, () -> service.create(playerBoxDTO));
    }

    @Test
    void testCreateWithBoxNotExistent() {
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        final BoxDTO boxDTO = new BoxDTO();
        boxDTO.setId(UUID.randomUUID());
        playerBoxDTO.setBox(boxDTO);
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        assertThrowsExactly(ApiNotFoundException.class, () -> service.create(playerBoxDTO));
    }

    @Test
    void testPatchEntity() {
        final BoxDTO boxDTO = generateBox();
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        playerBoxDTO.setBox(boxDTO);
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        assertDoesNotThrow(() -> {
            final PlayerBoxDTO res = this.service.create(playerBoxDTO);
            res.setAmount(1);

            final PlayerBoxDTO patched = this.service.update(res);
            assertEquals(res.getAmount(), patched.getAmount());
        });
    }

    private BoxDTO generateBox() throws ApiException {
        final BoxDTO boxDTO = new BoxDTO();
        boxDTO.setBoxName(UUID.randomUUID().toString());
        boxDTO.setBoxDescription(UUID.randomUUID().toString());
        boxDTO.setBoxDisplayName(UUID.randomUUID().toString());
        boxDTO.setDropAmount(new Random().nextInt(100));
        boxDTO.setGameMode(ServerGameMode.SKYBLOCK);

        return this.boxService.create(boxDTO);
    }

}
