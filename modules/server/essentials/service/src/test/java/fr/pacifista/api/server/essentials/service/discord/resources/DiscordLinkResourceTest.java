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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiscordLinkResourceTest {

    private final static String ROUTE = "/" + DiscordLinkClientImpl.PATH;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
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

        mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(discordLinkDTO)))
                .andExpect(status().isUnauthorized());

        MvcResult result = mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(discordLinkDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final DiscordLinkDTO response = jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class);
        assertEquals(discordLinkDTO.getDiscordUserId(), response.getDiscordUserId());
        assertEquals(discordLinkDTO.getMinecraftUuid(), response.getMinecraftUuid());
        assertNotNull(response.getLinkingKey());
        assertFalse(response.getIsLinked());

        response.setDiscordUserId("testnewID" + UUID.randomUUID());
        response.setIsLinked(true);

        result = mockMvc.perform(patch(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk())
                .andReturn();
        final DiscordLinkDTO responsePatched = jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class);

        assertEquals(response.getDiscordUserId(), responsePatched.getDiscordUserId());
        assertEquals(response.getIsLinked(), responsePatched.getIsLinked());
    }

    @Test
    void testCreationLinkAlreadyLinked() throws Exception {
        final UUID discordId1 = UUID.randomUUID();
        final UUID discordId2 = UUID.randomUUID();
        final UUID minecraftUuidAccount1 = UUID.randomUUID();
        final UUID minecraftUuidAccount2 = UUID.randomUUID();

        final DiscordLinkDTO pacifistaWebUserLinkDTO = new DiscordLinkDTO(
                discordId1.toString(),
                minecraftUuidAccount1
        );

        MvcResult result = mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isOk()).andReturn();
        DiscordLinkDTO resultDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class);

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete(ROUTE + "?id=" + resultDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        result = mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isOk()).andReturn();
        resultDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), DiscordLinkDTO.class);

        final DiscordLinkDTO pacifistaWebUserLinkDTO2 = new DiscordLinkDTO(
                discordId2.toString(),
                minecraftUuidAccount1
        );

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO2)))
                .andExpect(status().isBadRequest());

        final DiscordLinkDTO pacifistaWebUserLinkDTO3 = new DiscordLinkDTO(
                discordId1.toString(),
                minecraftUuidAccount2
        );

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO3)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete(ROUTE + "?id=" + resultDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO2)))
                .andExpect(status().isOk());
    }

}
