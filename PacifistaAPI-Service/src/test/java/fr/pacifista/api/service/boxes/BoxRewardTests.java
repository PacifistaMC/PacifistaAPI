package fr.pacifista.api.service.boxes;

import fr.pacifista.api.client.enums.ServerGameMode;
import fr.pacifista.api.client.modules.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.modules.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.boxes.services.BoxService;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoxRewardTests extends PacifistaServiceTest<BoxRewardDTO> {

    private static final String ROUTE = "/box/rewards";

    @Autowired
    private BoxService boxService;

    public BoxRewardTests() {
        super(BoxRewardDTO.class);
    }

    @Test
    public void testCreationReward() throws Exception {
        BoxDTO box = new BoxDTO();
        box.setBoxName("testBoxReward");
        box.setGameMode(ServerGameMode.SURVIVAL);
        box = this.boxService.create(box);

        final BoxRewardDTO request = new BoxRewardDTO();
        request.setBoxId(box.getId());
        request.setRarity(10.f);
        request.setItemSerialized("lkqdhlh");

        final BoxRewardDTO response = super.sendPostRequest(ROUTE, request, status().isOk());
        assertEquals(request.getBoxId(), response.getBoxId());
        assertEquals(request.getRarity(), response.getRarity());
        assertEquals(request.getItemSerialized(), response.getItemSerialized());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
        assertNull(response.getUpdatedAt());
    }
}
