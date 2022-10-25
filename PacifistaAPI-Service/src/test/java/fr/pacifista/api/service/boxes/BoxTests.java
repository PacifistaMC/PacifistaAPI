package fr.pacifista.api.service.boxes;

import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoxTests extends PacifistaServiceTest {

    private static final String ROUTE = "/box";

    @Test
    public void testBoxCreation() throws Exception {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("testBox");
        request.setBoxDescription("desc");
        request.setDropAmount(10);
        request.setBoxDisplayName("display");
        request.setGameMode(ServerGameMode.SURVIVAL);

        final BoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), BoxDTO.class);
        assertEquals(request.getBoxName(), response.getBoxName());
        assertEquals(request.getGameMode(), response.getGameMode());
        assertEquals(request.getBoxDescription(), response.getBoxDescription());
        assertEquals(request.getBoxDisplayName(), response.getBoxDisplayName());
        assertEquals(request.getDropAmount(), response.getDropAmount());
        assertNotNull(response.getCreatedAt());
        assertNull(request.getUpdatedAt());
        assertNotNull(response.getId());
    }

    @Test
    public void testBoxPatch() throws Exception {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("testBox");
        request.setBoxDescription("description");
        request.setBoxDisplayName("display");
        request.setGameMode(ServerGameMode.SURVIVAL);

        final BoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), BoxDTO.class);
        response.setBoxName("oui");

        final BoxDTO responsePatched = super.sendPatchRequest(ROUTE, response, status().isOk(), BoxDTO.class);
        assertEquals(response.getBoxName(), responsePatched.getBoxName());
    }

    @Test
    public void testBoxDelete() throws Exception {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("testBoxDelete");
        request.setBoxDescription("description");
        request.setBoxDisplayName("display");
        request.setGameMode(ServerGameMode.SURVIVAL);

        final BoxDTO response = super.sendPostRequest(ROUTE, request, status().isOk(), BoxDTO.class);
        super.sendDeleteRequest(ROUTE + "?id=" + response.getId(), status().isOk());
    }
}
