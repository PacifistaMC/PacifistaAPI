package fr.pacifista.api.server.essentials.service.command_sender.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendClientImpl;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommandToSendResourceTest {

    private final String route = "/" + CommandToSendClientImpl.PATH;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final CommandToSendDTO commandToSendDTO = new CommandToSendDTO(
                "role set funixgaming streamer partenaire ptet un jour sur twitch ?",
                ServerType.SURVIE_ALPHA,
                false,
                "testspringboot"
        );

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(commandToSendDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final CommandToSendDTO response = jsonHelper.fromJson(result.getResponse().getContentAsString(), CommandToSendDTO.class);
        assertEquals(commandToSendDTO.getCommand(), response.getCommand());
        assertEquals(commandToSendDTO.getServerType(), response.getServerType());
        assertFalse(response.getExecuted());

        response.setExecuted(true);
        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(response.getExecuted(), jsonHelper.fromJson(result.getResponse().getContentAsString(), CommandToSendDTO.class).getExecuted());
    }

}
