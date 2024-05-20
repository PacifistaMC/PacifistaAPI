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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaWebUserLinkResourceTest {

    private static final String ROUTE = "/" + PacifistaWebUserLinkClientImpl.PATH;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @MockBean
    private PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);

        final UUID minecraftUuid = UUID.randomUUID();

        final PacifistaPlayerDataDTO pacifistaPlayerDataDTO = new PacifistaPlayerDataDTO();
        pacifistaPlayerDataDTO.setMinecraftUuid(minecraftUuid);
        pacifistaPlayerDataDTO.setMinecraftUsername(UUID.randomUUID().toString());
        pacifistaPlayerDataDTO.setId(UUID.randomUUID());

        when(pacifistaPlayerDataInternalClient.getAll(any(), any(), any(), any()))
                .thenReturn(new PageDTO<>(List.of(pacifistaPlayerDataDTO), 1, 1, 1L, 1));
    }

    @Test
    void testCrud() throws Exception {
        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO = new PacifistaWebUserLinkDTO(
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isUnauthorized());

        MvcResult result = mockMvc.perform(post(ROUTE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTOResult = jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);

        assertEquals(pacifistaWebUserLinkDTO.getFunixProdUserId(), pacifistaWebUserLinkDTOResult.getFunixProdUserId());
        assertEquals(pacifistaWebUserLinkDTO.getMinecraftUuid(), pacifistaWebUserLinkDTOResult.getMinecraftUuid());
        assertNotNull(pacifistaWebUserLinkDTOResult.getLinkKey());
        assertFalse(pacifistaWebUserLinkDTOResult.getLinked());

        pacifistaWebUserLinkDTOResult.setLinked(true);

        result = mockMvc.perform(patch(ROUTE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(pacifistaWebUserLinkDTOResult)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTOResultUpdated = jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);

        assertTrue(pacifistaWebUserLinkDTOResultUpdated.getLinked());

        mockMvc.perform(delete(ROUTE + "?id=" + pacifistaWebUserLinkDTOResultUpdated.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
        mockMvc.perform(delete(ROUTE + "?id=" + pacifistaWebUserLinkDTOResultUpdated.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreationLinkAlreadyLinked() throws Exception {
        final UUID funixProdId1 = UUID.randomUUID();
        final UUID funixProdId2 = UUID.randomUUID();
        final UUID minecraftUuidAccount1 = UUID.randomUUID();
        final UUID minecraftUuidAccount2 = UUID.randomUUID();

        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO = new PacifistaWebUserLinkDTO(
                funixProdId1,
                minecraftUuidAccount1
        );

        MvcResult result = mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO)))
                .andExpect(status().isOk()).andReturn();
        PacifistaWebUserLinkDTO resultDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);

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
        resultDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaWebUserLinkDTO.class);

        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO2 = new PacifistaWebUserLinkDTO(
                funixProdId2,
                minecraftUuidAccount1
        );

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaWebUserLinkDTO2)))
                .andExpect(status().isBadRequest());

        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO3 = new PacifistaWebUserLinkDTO(
                funixProdId1,
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
