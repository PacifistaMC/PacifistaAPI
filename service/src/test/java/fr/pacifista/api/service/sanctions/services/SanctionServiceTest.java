package fr.pacifista.api.service.sanctions.services;

import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SanctionServiceTest {

    @Autowired
    SanctionService sanctionService;

    @Test
    void createEntity() {
        final SanctionDTO sanctionDTO = new SanctionDTO();
        sanctionDTO.setPlayerSanctionUuid(UUID.randomUUID());
        sanctionDTO.setPlayerSanctionIp("10.0.0.1");
        sanctionDTO.setReason("no limit");
        sanctionDTO.setSanctionType(SanctionType.BAN);
        sanctionDTO.setPlayerActionUuid(UUID.randomUUID());
        sanctionDTO.setPlayerActionIp("10.0.0.1");
        sanctionDTO.setIpSanction(true);
        sanctionDTO.setActive(false);
        sanctionDTO.setExpirationDate(null);

        assertDoesNotThrow(() -> {
            final SanctionDTO sanction = sanctionService.create(sanctionDTO);

            assertEquals(sanctionDTO.getPlayerSanctionUuid(), sanction.getPlayerSanctionUuid());
            assertEquals(sanctionDTO.getPlayerSanctionIp(), sanction.getPlayerSanctionIp());
            assertEquals(sanctionDTO.getReason(), sanction.getReason());
            assertEquals(sanctionDTO.getSanctionType(), sanction.getSanctionType());
            assertEquals(sanctionDTO.getPlayerActionUuid(), sanction.getPlayerActionUuid());
            assertEquals(sanctionDTO.getPlayerActionIp(), sanction.getPlayerActionIp());
            assertEquals(sanctionDTO.getIpSanction(), sanction.getIpSanction());
            assertEquals(sanctionDTO.getExpirationDate(), sanction.getExpirationDate());
            assertEquals(sanctionDTO.getActive(), sanction.getActive());
        });
    }

    @Test
    void updateEntity() {
        final SanctionDTO sanctionDTO = new SanctionDTO();
        sanctionDTO.setPlayerSanctionUuid(UUID.randomUUID());
        sanctionDTO.setPlayerSanctionIp("10.0.0.1");
        sanctionDTO.setReason("no limit");
        sanctionDTO.setSanctionType(SanctionType.BAN);
        sanctionDTO.setPlayerActionUuid(UUID.randomUUID());
        sanctionDTO.setPlayerActionIp("10.0.0.1");
        sanctionDTO.setIpSanction(true);
        sanctionDTO.setActive(false);
        sanctionDTO.setExpirationDate(null);

        assertDoesNotThrow(() -> {
            final SanctionDTO sanction = sanctionService.create(sanctionDTO);
            sanction.setReason("PATCHED");
            final SanctionDTO patched = sanctionService.update(sanction);
            assertEquals(sanction.getReason(), patched.getReason());
        });
    }

    @Test
    void testCheckIfPLayerIsSanctionedBan() {
        final UUID playerUUID = UUID.randomUUID();
        final SanctionDTO playerSanction = generateSanctionForPlayer(playerUUID, SanctionType.BAN);

        assertDoesNotThrow(() -> {
            final SanctionDTO isSanctioned = sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.BAN);
            assertEquals(playerSanction, isSanctioned);

            isSanctioned.setActive(false);
            sanctionService.update(isSanctioned);

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.BAN);
            });
        });
    }

    @Test
    void testCheckIfPLayerIsSanctionedBanExpired() {
        final UUID playerUUID = UUID.randomUUID();
        final SanctionDTO playerSanction = generateSanctionForPlayer(playerUUID, SanctionType.BAN);

        assertDoesNotThrow(() -> {
            final SanctionDTO isSanctioned = sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.BAN);
            assertEquals(playerSanction, isSanctioned);

            isSanctioned.setActive(true);
            isSanctioned.setExpirationDate(Date.from(Instant.now().minusSeconds(1000)));
            sanctionService.update(isSanctioned);

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.BAN);
            });
        });
    }

    @Test
    void testCheckIfPLayerIsSanctionedMute() {
        final UUID playerUUID = UUID.randomUUID();
        final SanctionDTO playerSanction = generateSanctionForPlayer(playerUUID, SanctionType.MUTE);

        assertDoesNotThrow(() -> {
            final SanctionDTO isSanctioned = sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.MUTE);
            assertEquals(playerSanction, isSanctioned);

            isSanctioned.setActive(false);
            sanctionService.update(isSanctioned);

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                sanctionService.isPlayerSanctioned(playerUUID.toString(), SanctionType.MUTE);
            });
        });
    }

    @Test
    void testCheckIfIpBan() {
        final UUID playerUUID = UUID.randomUUID();
        final SanctionDTO playerSanction = generateSanctionForPlayer(playerUUID, SanctionType.BAN);

        assertDoesNotThrow(() -> {
            final SanctionDTO isSanctioned = sanctionService.isIpSanctioned(playerSanction.getPlayerSanctionIp(), SanctionType.BAN);
            assertEquals(playerSanction, isSanctioned);

            isSanctioned.setActive(false);
            sanctionService.update(isSanctioned);

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                sanctionService.isIpSanctioned(playerSanction.getPlayerSanctionIp(), SanctionType.BAN);
            });
        });
    }

    private SanctionDTO generateSanctionForPlayer(final UUID playerUUID, final SanctionType sanctionType) {
        final SanctionDTO sanctionDTO = new SanctionDTO();
        sanctionDTO.setPlayerSanctionUuid(playerUUID);
        sanctionDTO.setPlayerSanctionIp("10.0.0.1");
        sanctionDTO.setReason("no limit");
        sanctionDTO.setSanctionType(sanctionType);
        sanctionDTO.setPlayerActionUuid(UUID.randomUUID());
        sanctionDTO.setPlayerActionIp("10.0.0.1");
        sanctionDTO.setIpSanction(true);
        sanctionDTO.setActive(true);
        sanctionDTO.setExpirationDate(null);

        return sanctionService.create(sanctionDTO);
    }

}
