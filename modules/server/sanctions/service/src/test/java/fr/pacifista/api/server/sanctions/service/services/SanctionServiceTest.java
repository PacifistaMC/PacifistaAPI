package fr.pacifista.api.server.sanctions.service.services;

import com.funixproductions.api.encryption.client.clients.FunixProductionsEncryptionClient;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class SanctionServiceTest {

    @Autowired
    SanctionService sanctionService;

    @MockBean
    FunixProductionsEncryptionClient encryptionClient;

    @BeforeEach
    void setupMocks() {
        when(encryptionClient.encrypt(anyString())).thenReturn(UUID.randomUUID().toString());
        when(encryptionClient.decrypt(anyString())).thenReturn(UUID.randomUUID().toString());
    }

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
