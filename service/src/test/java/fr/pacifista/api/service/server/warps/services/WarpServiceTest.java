package fr.pacifista.api.service.server.warps.services;

import fr.pacifista.api.client.core.enums.ServerType;
import fr.pacifista.api.client.server.warps.dtos.WarpDTO;
import fr.pacifista.api.client.server.warps.enums.WarpType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WarpServiceTest {

    @Autowired
    WarpService warpService;

    @Test
    void createEntity() {
        final WarpDTO warpDTO = new WarpDTO();
        warpDTO.setName(UUID.randomUUID().toString());
        warpDTO.setWarpItem("minecraft:stone");
        warpDTO.setPublicAccess(true);
        warpDTO.setPlayerOwnerUuid(UUID.randomUUID());
        warpDTO.setType(WarpType.PLAYER_WARP);
        warpDTO.setWorldUuid(UUID.randomUUID());
        warpDTO.setServerType(ServerType.CREATIVE);
        warpDTO.setX(0.0);
        warpDTO.setY(0.0);
        warpDTO.setZ(0.0);
        warpDTO.setYaw(0.0f);
        warpDTO.setPitch(0.0f);

        assertDoesNotThrow(() -> {
            final WarpDTO createdWarpDTO = warpService.create(warpDTO);
            assertEquals(warpDTO.getName(), createdWarpDTO.getName());
            assertEquals(warpDTO.getWarpItem(), createdWarpDTO.getWarpItem());
            assertEquals(warpDTO.getPublicAccess(), createdWarpDTO.getPublicAccess());
            assertEquals(warpDTO.getPlayerOwnerUuid(), createdWarpDTO.getPlayerOwnerUuid());
            assertEquals(warpDTO.getType(), createdWarpDTO.getType());
            assertEquals(warpDTO.getWorldUuid(), createdWarpDTO.getWorldUuid());
            assertEquals(warpDTO.getServerType(), createdWarpDTO.getServerType());
            assertEquals(warpDTO.getX(), createdWarpDTO.getX());
            assertEquals(warpDTO.getY(), createdWarpDTO.getY());
            assertEquals(warpDTO.getZ(), createdWarpDTO.getZ());
            assertEquals(warpDTO.getYaw(), createdWarpDTO.getYaw());
            assertEquals(warpDTO.getPitch(), createdWarpDTO.getPitch());
        });
    }

    @Test
    void patchEntity() {
        final WarpDTO warpDTO = new WarpDTO();
        warpDTO.setName(UUID.randomUUID().toString());
        warpDTO.setWarpItem("minecraft:stone");
        warpDTO.setPublicAccess(true);
        warpDTO.setPlayerOwnerUuid(UUID.randomUUID());
        warpDTO.setType(WarpType.PLAYER_WARP);
        warpDTO.setWorldUuid(UUID.randomUUID());
        warpDTO.setServerType(ServerType.CREATIVE);
        warpDTO.setX(0.0);
        warpDTO.setY(0.0);
        warpDTO.setZ(0.0);
        warpDTO.setYaw(0.0f);
        warpDTO.setPitch(0.0f);

        assertDoesNotThrow(() -> {
            final WarpDTO createdWarpDTO = warpService.create(warpDTO);
            createdWarpDTO.setName(UUID.randomUUID().toString());

            final WarpDTO patchedWarpDTO = warpService.update(createdWarpDTO);
            assertEquals(createdWarpDTO.getName(), patchedWarpDTO.getName());
        });
    }

}
