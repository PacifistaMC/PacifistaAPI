package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.service.server.boxes.services.BoxService;
import fr.pacifista.api.service.server.boxes.services.PlayerBoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
class BoxServiceTest {

    @Autowired
    BoxService service;

    @Autowired
    PlayerBoxService playerBoxService;

    @Test
    void testCreateBox() {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("name" + UUID.randomUUID());
        request.setBoxDescription("desc" + UUID.randomUUID());
        request.setBoxDisplayName("dpname" + UUID.randomUUID());
        request.setDropAmount(10);
        request.setGameMode(ServerGameMode.SKYBLOCK);

        final BoxDTO response = this.service.create(request);
        assertEquals(response.getBoxName(), response.getBoxName());
        assertEquals(response.getBoxDescription(), response.getBoxDescription());
        assertEquals(response.getBoxDisplayName(), response.getBoxDisplayName());
        assertEquals(response.getDropAmount(), response.getDropAmount());
        assertEquals(response.getGameMode(), response.getGameMode());
    }

    @Test
    void deleteBox() {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("name" + UUID.randomUUID());
        request.setBoxDescription("desc" + UUID.randomUUID());
        request.setBoxDisplayName("dpname" + UUID.randomUUID());
        request.setDropAmount(10);
        request.setGameMode(ServerGameMode.SKYBLOCK);

        final BoxDTO response = this.service.create(request);
        this.service.delete(response.getId().toString());
        assertThrowsExactly(ApiNotFoundException.class, () -> this.service.findById(response.getId().toString()));
    }

    @Test
    void deleteBoxWithPlayerBox() {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("name" + UUID.randomUUID());
        request.setBoxDescription("desc" + UUID.randomUUID());
        request.setBoxDisplayName("dpname" + UUID.randomUUID());
        request.setDropAmount(10);
        request.setGameMode(ServerGameMode.SKYBLOCK);

        final BoxDTO response = this.service.create(request);

        final PlayerBoxDTO playerBox = new PlayerBoxDTO();
        playerBox.setBox(response);
        playerBox.setPlayerUuid(UUID.randomUUID());
        playerBox.setAmount(10);
        final PlayerBoxDTO playerBoxResponse = this.playerBoxService.create(playerBox);

        this.service.delete(response.getId().toString());
        assertThrowsExactly(ApiNotFoundException.class, () -> this.playerBoxService.findById(playerBoxResponse.getId().toString()));
    }

}
