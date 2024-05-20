package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaWebUserLinkResourcePublicTest {

    private static final String ROUTE = "/" + PacifistaWebUserLinkClientImpl.PATH + "/public";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private PacifistaWebUserLinkService service;

    @MockBean
    private UserAuthClient userAuthClient;

    @MockBean
    private PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient;

    @Test
    void testNormalCreationLink() throws Exception {
        final UserDTO user = setupUserDTO();
        final UUID minecraftUuid = UUID.randomUUID();

        final PacifistaPlayerDataDTO pacifistaPlayerDataDTO = new PacifistaPlayerDataDTO();
        pacifistaPlayerDataDTO.setMinecraftUuid(minecraftUuid);
        pacifistaPlayerDataDTO.setMinecraftUsername(UUID.randomUUID().toString());
        pacifistaPlayerDataDTO.setId(UUID.randomUUID());

        when(pacifistaPlayerDataInternalClient.getAll(any(), any(), any(), any()))
                .thenReturn(new PageDTO<>(List.of(pacifistaPlayerDataDTO), 1, 1, 1L, 1));

        mockMvc.perform(post(ROUTE + "/link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(minecraftUuid)))
                .andExpect(status().isUnauthorized());

        MvcResult mvcResult = mockMvc.perform(post(ROUTE + "/link")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(minecraftUuid)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);

        assertEquals(user.getId().toString(), pacifistaWebUserLinkDTO.getFunixProdUserId());
        assertEquals(minecraftUuid.toString(), pacifistaWebUserLinkDTO.getMinecraftUuid());
        assertFalse(pacifistaWebUserLinkDTO.getLinked());
        assertNotNull(pacifistaWebUserLinkDTO.getLinkKey());
        assertEquals(pacifistaPlayerDataDTO.getMinecraftUsername(), pacifistaWebUserLinkDTO.getMinecraftUsername());

        mockMvc.perform(post(ROUTE + "/link")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(minecraftUuid)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(ROUTE + "/linked"))
                .andExpect(status().isUnauthorized());

        mvcResult = mockMvc.perform(get(ROUTE + "/linked")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk()).andReturn();
        PacifistaWebUserLinkDTO linked = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);
        assertEquals(user.getId().toString(), linked.getFunixProdUserId());
        assertEquals(minecraftUuid.toString(), linked.getMinecraftUuid());
        assertEquals(pacifistaWebUserLinkDTO.getLinkKey(), linked.getLinkKey());
        assertEquals(pacifistaWebUserLinkDTO.getLinked(), linked.getLinked());
        assertEquals(pacifistaPlayerDataDTO.getMinecraftUsername(), linked.getMinecraftUsername());

        confirmLink(pacifistaWebUserLinkDTO);

        mvcResult = mockMvc.perform(get(ROUTE + "/linked")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk()).andReturn();
        linked = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);
        assertEquals(user.getId().toString(), linked.getFunixProdUserId());
        assertEquals(minecraftUuid.toString(), linked.getMinecraftUuid());
        assertEquals(pacifistaWebUserLinkDTO.getLinkKey(), linked.getLinkKey());
        assertTrue(linked.getLinked());

        mockMvc.perform(post(ROUTE + "/unlink"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post(ROUTE + "/unlink")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk());

        mockMvc.perform(get(ROUTE + "/linked")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isNotFound());
    }

    private UserDTO setupUserDTO() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.USER);
        when(userAuthClient.current(anyString())).thenReturn(userDTO);
        return userDTO;
    }

    private void confirmLink(final PacifistaWebUserLinkDTO createdLink) {
        createdLink.setLinked(true);
        service.update(createdLink);
    }

}
