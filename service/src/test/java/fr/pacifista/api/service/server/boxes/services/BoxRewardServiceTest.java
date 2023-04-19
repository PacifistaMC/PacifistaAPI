package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.service.server.boxes.services.BoxRewardService;
import fr.pacifista.api.service.server.boxes.services.BoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoxRewardServiceTest {

    @Autowired
    BoxRewardService service;

    @Autowired
    BoxService boxService;

    @Test
    void testCreateEntity() {
        final BoxRewardDTO request = new BoxRewardDTO();
        final BoxDTO box = generateBox();

        request.setBoxId(box.getId());
        request.setItemSerialized(UUID.randomUUID().toString());
        request.setRarity(1.2f);

        final BoxRewardDTO response = this.service.create(request);
        assertEquals(request.getBoxId(), response.getBoxId());
        assertEquals(request.getRarity(), response.getRarity());
        assertEquals(request.getItemSerialized(), response.getItemSerialized());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
        assertNull(response.getUpdatedAt());
    }

    @Test
    void testCreateEntityWithNoBox() {
        final BoxRewardDTO request = new BoxRewardDTO();
        request.setItemSerialized(UUID.randomUUID().toString());
        request.setRarity(1.2f);

        assertThrowsExactly(ApiBadRequestException.class, () -> this.service.create(request));
    }

    @Test
    void testCreateEntityWithBoxNotFound() {
        final BoxRewardDTO request = new BoxRewardDTO();
        request.setBoxId(UUID.randomUUID());
        request.setItemSerialized(UUID.randomUUID().toString());
        request.setRarity(1.2f);

        assertThrowsExactly(ApiNotFoundException.class, () -> this.service.create(request));
    }

    @Test
    void testPatch() {
        final float rarity = 10.4f;
        BoxRewardDTO request = new BoxRewardDTO();
        request.setBoxId(generateBox().getId());
        request.setRarity(1.4f);
        request.setItemSerialized(UUID.randomUUID().toString());

        request = this.service.create(request);
        request.setRarity(rarity);

        final BoxRewardDTO patched = this.service.update(request);
        assertEquals(rarity, patched.getRarity());
    }

    private BoxDTO generateBox() {
        final BoxDTO boxDTO = new BoxDTO();
        boxDTO.setBoxName("name" + UUID.randomUUID());
        boxDTO.setBoxDescription("desc" + UUID.randomUUID());
        boxDTO.setBoxDisplayName("dname" + UUID.randomUUID());
        boxDTO.setDropAmount(10);
        boxDTO.setGameMode(ServerGameMode.SKYBLOCK);

        return this.boxService.create(boxDTO);
    }

}
