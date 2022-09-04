package fr.pacifista.api.service.boxes;

import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.service.boxes.services.BoxService;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerBoxTests extends PacifistaServiceTest {

    private static final String ROUTE = "/box/player";

    @Autowired
    private BoxService boxService;

    @Test
    public void testCreationPlayerBox() throws Exception {
        BoxDTO box = new BoxDTO();
        box.setBoxName("testPlayerBox");
        box.setGameMode(ServerGameMode.SURVIVAL);
        box = this.boxService.create(box);

        final PlayerBoxDTO request = new PlayerBoxDTO();
        request.setBox(box);
        request.setPlayerUuid(UUID.randomUUID());
        request.setAmount(10);

        final PlayerBoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), PlayerBoxDTO.class);
        assertEquals(request.getBox().getId(), response.getBox().getId());
        assertEquals(request.getPlayerUuid(), response.getPlayerUuid());
        assertEquals(request.getAmount(), response.getAmount());
    }

    @Test
    public void testPatchPlayerBox() throws Exception {
        BoxDTO box = new BoxDTO();
        box.setBoxName("testPlayerBox2");
        box.setGameMode(ServerGameMode.SURVIVAL);
        box = this.boxService.create(box);

        final PlayerBoxDTO request = new PlayerBoxDTO();
        request.setBox(box);
        request.setPlayerUuid(UUID.randomUUID());
        request.setAmount(10);

        final PlayerBoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), PlayerBoxDTO.class);
        response.setAmount(11);

        final PlayerBoxDTO responsePatched = super.sendPatchRequest(ROUTE, response, status().isOk(), PlayerBoxDTO.class);
        assertEquals(response.getAmount(), responsePatched.getAmount());
    }
}
