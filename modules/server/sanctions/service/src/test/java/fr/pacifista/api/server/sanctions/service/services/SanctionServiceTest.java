package fr.pacifista.api.server.sanctions.service.services;

import com.funixproductions.api.encryption.client.clients.EncryptionClient;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class SanctionServiceTest {

    @Autowired
    SanctionService sanctionService;

    @MockBean
    EncryptionClient encryptionClient;

    @BeforeEach
    void setupMocks() {
        when(encryptionClient.encrypt(anyString())).thenReturn(UUID.randomUUID().toString());
        when(encryptionClient.decrypt(anyString())).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    void createEntity() {
        final SanctionDTO sanctionDTO = new SanctionDTO();
        sanctionDTO.setPlayerSanctionUuid(UUID.randomUUID());
        sanctionDTO.setReason("no limit");
        sanctionDTO.setSanctionType(SanctionType.BAN);
        sanctionDTO.setPlayerActionUuid(UUID.randomUUID());
        sanctionDTO.setActive(false);
        sanctionDTO.setExpirationDate(null);

        assertDoesNotThrow(() -> {
            final SanctionDTO sanction = sanctionService.create(sanctionDTO);

            assertEquals(sanctionDTO.getPlayerSanctionUuid(), sanction.getPlayerSanctionUuid());
            assertEquals(sanctionDTO.getReason(), sanction.getReason());
            assertEquals(sanctionDTO.getSanctionType(), sanction.getSanctionType());
            assertEquals(sanctionDTO.getPlayerActionUuid(), sanction.getPlayerActionUuid());
            assertEquals(sanctionDTO.getExpirationDate(), sanction.getExpirationDate());
            assertEquals(sanctionDTO.getActive(), sanction.getActive());
        });
    }

    @Test
    void updateEntity() {
        final SanctionDTO sanctionDTO = new SanctionDTO();
        sanctionDTO.setPlayerSanctionUuid(UUID.randomUUID());
        sanctionDTO.setReason("no limit");
        sanctionDTO.setSanctionType(SanctionType.BAN);
        sanctionDTO.setPlayerActionUuid(UUID.randomUUID());
        sanctionDTO.setActive(false);
        sanctionDTO.setExpirationDate(null);

        assertDoesNotThrow(() -> {
            final SanctionDTO sanction = sanctionService.create(sanctionDTO);
            sanction.setReason("PATCHED");
            final SanctionDTO patched = sanctionService.update(sanction);
            assertEquals(sanction.getReason(), patched.getReason());
        });
    }

}
