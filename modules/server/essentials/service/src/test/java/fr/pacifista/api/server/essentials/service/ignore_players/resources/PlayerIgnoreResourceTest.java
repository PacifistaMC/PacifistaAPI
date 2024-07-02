package fr.pacifista.api.server.essentials.service.ignore_players.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.essentials.client.ingore_players.clients.PlayerIgnoreClientImpl;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerIgnoreResourceTest {

    private final String route = "/" + PlayerIgnoreClientImpl.PATH;

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
        final PlayerIgnoreDTO playerIgnoreDTO = new PlayerIgnoreDTO(UUID.randomUUID(), UUID.randomUUID());

        MvcResult res = mockMvc.perform(post(route)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(playerIgnoreDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final PlayerIgnoreDTO createdPlayerIgnoreDTO = jsonHelper.fromJson(res.getResponse().getContentAsString(), PlayerIgnoreDTO.class);
        assertEquals(playerIgnoreDTO.getPlayerId(), createdPlayerIgnoreDTO.getPlayerId());
        assertEquals(playerIgnoreDTO.getIgnoredPlayerId(), createdPlayerIgnoreDTO.getIgnoredPlayerId());

        mockMvc.perform(post(route)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(new PlayerIgnoreDTO())))
                .andExpect(status().isBadRequest());

        createdPlayerIgnoreDTO.setIgnoredPlayerId(UUID.randomUUID());
        res = mockMvc.perform(patch(route)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(createdPlayerIgnoreDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final PlayerIgnoreDTO updatedPlayerIgnoreDTO = jsonHelper.fromJson(res.getResponse().getContentAsString(), PlayerIgnoreDTO.class);
        assertEquals(createdPlayerIgnoreDTO.getIgnoredPlayerId(), updatedPlayerIgnoreDTO.getIgnoredPlayerId());

        mockMvc.perform(delete(route + "?id=" + updatedPlayerIgnoreDTO.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
        mockMvc.perform(delete(route + "?id=" + updatedPlayerIgnoreDTO.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

}
