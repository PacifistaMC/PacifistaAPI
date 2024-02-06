package fr.pacifista.api.serverplayers.data.service.resources;

import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;
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
class PacifistaPlayerDataResourceTest extends ResourceTestHandler {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Test
    void createAndEditDataSuccess() throws Exception {
        super.setupPacifistaAdmin();
        final PacifistaPlayerDataDTO playerDataDTO = new PacifistaPlayerDataDTO();
        playerDataDTO.setMinecraftUsername("Oui");
        playerDataDTO.setMinecraftUuid(UUID.randomUUID());
        playerDataDTO.setPlayTime(0L);
        playerDataDTO.setLastConnection(new Date());
        playerDataDTO.setFirstConnection(new Date());
        playerDataDTO.setAcceptPayments(true);
        playerDataDTO.setAcceptTeleportation(true);
        playerDataDTO.setAcceptPingSoundTagMessage(true);

        MvcResult mvcResult = mockMvc.perform(post("/playerdata/data")
                .header("Authorization", "Bearer dd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(playerDataDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerDataDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerDataDTO.class);

        response.setPlayTime(10L);
        mvcResult = mockMvc.perform(patch("/playerdata/data")
                        .header("Authorization", "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerDataDTO response2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerDataDTO.class);
        assertEquals(response.getPlayTime(), response2.getPlayTime());
    }

    @Test
    void accessRouteNoPermission() throws Exception {
        mockMvc.perform(post("/playerdata/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(new PacifistaPlayerDataDTO())))
                .andExpect(status().isUnauthorized());

        super.setupNormal();
        final PacifistaPlayerDataDTO playerDataDTO = new PacifistaPlayerDataDTO();
        playerDataDTO.setMinecraftUsername("Oui");
        playerDataDTO.setMinecraftUuid(UUID.randomUUID());
        playerDataDTO.setPlayTime(0L);
        playerDataDTO.setLastConnection(new Date());
        playerDataDTO.setFirstConnection(new Date());
        playerDataDTO.setAcceptPayments(true);
        playerDataDTO.setAcceptTeleportation(true);
        playerDataDTO.setAcceptPingSoundTagMessage(true);
        mockMvc.perform(post("/playerdata/data")
                        .header("Authorization", "Bearer dds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(playerDataDTO)))
                .andExpect(status().isForbidden());
    }

}
