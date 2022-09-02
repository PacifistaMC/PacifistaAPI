package fr.pacifista.api.service.sanctions;

import fr.pacifista.api.client.modules.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.modules.sanctions.enums.SanctionType;
import fr.pacifista.api.service.sanctions.services.SanctionService;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SanctionResourceTest extends PacifistaServiceTest {

    private static final String ROUTE = "/sanctions";

    @Autowired
    private SanctionService sanctionService;

    @Test
    public void testIsPlayerSanctionedSuccess() throws Exception {
        final SanctionDTO ban = createSanction(SanctionType.BAN, null, false);

        SanctionDTO response = super.sendGetRequest(
                ROUTE + "/isPlayerSanctioned?playerUuid=" +
                ban.getPlayerSanctionUuid() +
                "&sanctionType=" + SanctionType.BAN,
                status().isOk(),
                SanctionDTO.class
        );
        assertEquals(ban.getPlayerSanctionUuid(), response.getPlayerSanctionUuid());
        assertEquals(ban.getIpSanction(), response.getIpSanction());
        assertEquals(ban.getPlayerSanctionIp(), response.getPlayerSanctionIp());
        assertEquals(ban.getSanctionType(), response.getSanctionType());
        assertEquals(ban.getPlayerSanctionUuid(), response.getPlayerSanctionUuid());
        assertEquals(ban.getPlayerActionIp(), response.getPlayerActionIp());
        assertEquals(ban.getIpSanction(), response.getIpSanction());
        assertEquals(ban.getActive(), response.getActive());
        assertEquals(ban.getExpirationDate(), response.getExpirationDate());
        assertEquals(ban.getReason(), response.getReason());

        response = super.sendGetRequest(
                ROUTE + "/isPlayerSanctioned?playerUuid=" +
                        ban.getPlayerSanctionUuid() +
                        "&sanctionType=" + SanctionType.MUTE,
                status().isNotFound(),
                SanctionDTO.class
        );
    }

    @Test
    public void testIsPlayerSanctionedIpSuccess() throws Exception {
        final SanctionDTO mute = createSanction(SanctionType.MUTE, null, true);

        SanctionDTO response = super.sendGetRequest(
                ROUTE + "/isIpSanctioned?playerIp=" +
                        mute.getPlayerSanctionIp() +
                        "&sanctionType=" + SanctionType.BAN,
                status().isOk(),
                SanctionDTO.class
        );

        response = super.sendGetRequest(
                ROUTE + "/isIpSanctioned?playerIp=" +
                        mute.getPlayerActionIp() +
                        "&sanctionType=" + SanctionType.MUTE,
                status().isNotFound(),
                SanctionDTO.class
        );
    }

    @Test
    public void testExpiredSanctionPlayerSanction() throws Exception {
        final SanctionDTO mute = createSanction(SanctionType.MUTE, Date.from(Instant.now().minusSeconds(300)), false);

        SanctionDTO response = super.sendGetRequest(
                ROUTE + "/isPlayerSanctioned?playerUuid=" +
                        mute.getPlayerSanctionUuid() +
                        "&sanctionType=" + SanctionType.MUTE,
                status().isNotFound(),
                SanctionDTO.class
        );
    }

    @Test
    public void testValidSanctionWithExpirationDate() throws Exception {
        final SanctionDTO mute = createSanction(SanctionType.MUTE, Date.from(Instant.now().plusSeconds(300)), false);

        SanctionDTO response = super.sendGetRequest(
                ROUTE + "/isPlayerSanctioned?playerUuid=" +
                        mute.getPlayerSanctionUuid() +
                        "&sanctionType=" + SanctionType.MUTE,
                status().isOk(),
                SanctionDTO.class
        );
        assertEquals(mute.getExpirationDate(), response.getExpirationDate());
    }

    private SanctionDTO createSanction(@NonNull SanctionType sanctionType,
                                       @Nullable Date expirationDate,
                                       boolean ipSanction) {
        final SanctionDTO request = new SanctionDTO();
        request.setSanctionType(sanctionType);
        request.setPlayerSanctionUuid(UUID.randomUUID());
        request.setPlayerSanctionIp(UUID.randomUUID().toString()); //Random IP (using uuid)
        request.setReason("Test");
        request.setIpSanction(ipSanction);
        request.setActive(true);
        request.setExpirationDate(expirationDate);

        return sanctionService.create(request);
    }

}
