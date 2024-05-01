package fr.pacifista.api.server.essentials.service.discord.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.essentials.client.discord.clients.DiscordLinkClientImpl;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiscordLinkResourceTest {

    private final String route = "/" + DiscordLinkClientImpl.PATH;

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
        final DiscordLinkDTO discordLinkDTO = new DiscordLinkDTO("funixtest" + UUID.randomUUID(), UUID.randomUUID());

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(discordLinkDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final DiscordLinkDTO response = jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class);
        assertEquals(discordLinkDTO.getDiscordUserId(), response.getDiscordUserId());
        assertEquals(discordLinkDTO.getMinecraftUuid(), response.getMinecraftUuid());
        response.setDiscordUserId("testnewID" + UUID.randomUUID());

        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(response.getDiscordUserId(), jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class).getDiscordUserId());
    }

}
