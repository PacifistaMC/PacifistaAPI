package fr.pacifista.api.service.boxes;

import fr.pacifista.api.client.enums.ServerGameMode;
import fr.pacifista.api.client.modules.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoxTests extends PacifistaServiceTest<BoxDTO> {

    private static final String ROUTE = "/box";

    public BoxTests() {
        super(BoxDTO.class);
    }

    @Test
    public void testBoxCreation() throws Exception {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("testBox");
        request.setGameMode(ServerGameMode.SURVIVAL);

        final BoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk());
        assertEquals(request.getBoxName(), response.getBoxName());
        assertEquals(request.getGameMode(), response.getGameMode());
        assertNotNull(response.getCreatedAt());
        assertNull(request.getUpdatedAt());
        assertNotNull(response.getId());
    }
}
