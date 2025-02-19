package fr.pacifista.api.server.players.data.service.resources;

import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerChatMessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaPlayerChatMessageResourceTest extends ResourceTestHandler {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Test
    void createAndEditChatMessageSuccess() throws Exception {
        super.setupPacifistaAdmin();
        final PacifistaPlayerChatMessageDTO pacifistaPlayerChatMessageDTO = new PacifistaPlayerChatMessageDTO();
        pacifistaPlayerChatMessageDTO.setMessage("test");
        pacifistaPlayerChatMessageDTO.setIsCommand(false);
        pacifistaPlayerChatMessageDTO.setMinecraftUuid(UUID.randomUUID());
        pacifistaPlayerChatMessageDTO.setServerType(ServerType.CREATIVE);
        pacifistaPlayerChatMessageDTO.setMinecraftUsername("Oui");

        MvcResult mvcResult = mockMvc.perform(post("/playerdata/chatmessages")
                .header("Authorization", "Bearer dd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(pacifistaPlayerChatMessageDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerChatMessageDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerChatMessageDTO.class);
        assertEquals(pacifistaPlayerChatMessageDTO.getMinecraftUsername(), response.getMinecraftUsername());
        assertEquals(pacifistaPlayerChatMessageDTO.getMessage(), response.getMessage());
        assertEquals(pacifistaPlayerChatMessageDTO.getIsCommand(), response.getIsCommand());
        assertEquals(pacifistaPlayerChatMessageDTO.getServerType(), response.getServerType());
        assertEquals(pacifistaPlayerChatMessageDTO.getMinecraftUuid(), response.getMinecraftUuid());

        response.setMessage("dd");
        mvcResult = mockMvc.perform(patch("/playerdata/chatmessages")
                        .header("Authorization", "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaPlayerChatMessageDTO response2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaPlayerChatMessageDTO.class);
        assertEquals(response.getMessage(), response2.getMessage());
    }

    @Test
    void accessRouteNoPermission() throws Exception {
        mockMvc.perform(post("/playerdata/chatmessages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(new PacifistaPlayerChatMessageDTO())))
                .andExpect(status().isUnauthorized());

        super.setupNormal();
        final PacifistaPlayerChatMessageDTO pacifistaPlayerChatMessageDTO = new PacifistaPlayerChatMessageDTO();
        pacifistaPlayerChatMessageDTO.setMessage("test");
        pacifistaPlayerChatMessageDTO.setIsCommand(false);
        pacifistaPlayerChatMessageDTO.setMinecraftUuid(UUID.randomUUID());
        pacifistaPlayerChatMessageDTO.setServerType(ServerType.CREATIVE);
        pacifistaPlayerChatMessageDTO.setMinecraftUsername("Oui");
        mockMvc.perform(post("/playerdata/chatmessages")
                        .header("Authorization", "Bearer ddc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaPlayerChatMessageDTO)))
                .andExpect(status().isForbidden());
    }

}
