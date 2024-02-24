package fr.pacifista.api.server.players.data.service.resources;

import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerSessionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaPlayerSessionResourceTest extends ResourceTestHandler {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Test
    void createAndEditSessionSuccess() throws Exception {
        super.setupPacifistaAdmin();
        final PacifistaPlayerSessionDTO sessionDTO = new PacifistaPlayerSessionDTO();
        sessionDTO.setConnectedAt(new Date());
        sessionDTO.setDisconnectedAt(new Date());
        sessionDTO.setMinecraftUsername("Oui");
        sessionDTO.setMinecraftUuid(UUID.randomUUID());

        MvcResult mvcResult = mockMvc.perform(post("/playerdata/session")
                .header("Authorization", "Bearer dd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(sessionDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerSessionDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerSessionDTO.class);

        response.setMinecraftUsername("dd");
        mvcResult = mockMvc.perform(patch("/playerdata/session")
                        .header("Authorization", "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerSessionDTO response2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerSessionDTO.class);
        assertEquals(response.getMinecraftUsername(), response2.getMinecraftUsername());
    }

    @Test
    void accessRouteNoPermission() throws Exception {
        mockMvc.perform(post("/playerdata/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(new PacifistaPlayerDataDTO())))
                .andExpect(status().isUnauthorized());

        super.setupNormal();
        final PacifistaPlayerSessionDTO sessionDTO = new PacifistaPlayerSessionDTO();
        sessionDTO.setConnectedAt(new Date());
        sessionDTO.setDisconnectedAt(new Date());
        sessionDTO.setMinecraftUsername("Oui");
        sessionDTO.setMinecraftUuid(UUID.randomUUID());
        mockMvc.perform(post("/playerdata/session")
                        .header("Authorization", "Bearer ddz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(sessionDTO)))
                .andExpect(status().isForbidden());
    }

}
