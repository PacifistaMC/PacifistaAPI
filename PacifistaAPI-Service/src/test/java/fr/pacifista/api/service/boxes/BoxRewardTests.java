package fr.pacifista.api.service.boxes;

import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.service.boxes.services.BoxService;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoxRewardTests extends PacifistaServiceTest {

    private static final String ROUTE = "/box/rewards";

    @Autowired
    private BoxService boxService;

    @Test
    public void testCreationReward() throws Exception {
        BoxDTO box = new BoxDTO();
        box.setBoxName("testBoxReward");
        box.setBoxDescription("desc");
        box.setBoxDisplayName("display");
        box.setGameMode(ServerGameMode.SURVIVAL);
        box = this.boxService.create(box);

        final BoxRewardDTO request = new BoxRewardDTO();
        request.setBoxId(box.getId());
        request.setRarity(10.f);
        request.setItemSerialized("lkqdhlh");

        final BoxRewardDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), BoxRewardDTO.class);
        assertEquals(request.getBoxId(), response.getBoxId());
        assertEquals(request.getRarity(), response.getRarity());
        assertEquals(request.getItemSerialized(), response.getItemSerialized());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
        assertNull(response.getUpdatedAt());
    }

    @Test
    public void testPatchReward() throws Exception {
        BoxDTO box = new BoxDTO();
        box.setBoxName("testBoxReward2");
        box.setBoxDescription("desc");
        box.setBoxDisplayName("display");
        box.setGameMode(ServerGameMode.SURVIVAL);
        box = this.boxService.create(box);

        final BoxRewardDTO request = new BoxRewardDTO();
        request.setBoxId(box.getId());
        request.setRarity(10.f);
        request.setItemSerialized("lkqdhlh");

        final BoxRewardDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), BoxRewardDTO.class);
        response.setRarity(11.f);

        final BoxRewardDTO response2 = super.sendPatchRequest(ROUTE, response, status().isOk(), BoxRewardDTO.class);
        assertEquals(response.getRarity(), response2.getRarity());

        response2.setItemSerialized("blavla");
        final BoxRewardDTO response3 = super.sendPatchRequest(ROUTE, response2, status().isOk(), BoxRewardDTO.class);
        assertEquals(response2.getItemSerialized(), response3.getItemSerialized());
    }
}
