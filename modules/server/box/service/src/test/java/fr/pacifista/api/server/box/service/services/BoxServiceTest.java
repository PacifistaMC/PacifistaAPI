package fr.pacifista.api.server.box.service.services;

import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
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
        assertEquals(request.getBoxName(), response.getBoxName());
        assertEquals(request.getBoxDescription(), response.getBoxDescription());
        assertEquals(request.getBoxDisplayName(), response.getBoxDisplayName());
        assertEquals(request.getDropAmount(), response.getDropAmount());
        assertEquals(request.getGameMode(), response.getGameMode());
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
