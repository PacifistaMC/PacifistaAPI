package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.server.warps.client.clients.WarpClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.client.enums.WarpType;
import fr.pacifista.api.server.warps.service.repositories.WarpConfigRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WarpResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private WarpConfigRepository warpConfigRepository;

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
        final WarpDTO warpCreationRequest = new WarpDTO(
                UUID.randomUUID().toString(),
                "test",
                "STONE",
                UUID.randomUUID(),
                WarpType.PLAYER_WARP
        );
        LocationDTO.fillWithRandomValuesForTestingPurposes(warpCreationRequest);

        MvcResult result = mockMvc.perform(
                post("/" + WarpClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(warpCreationRequest))
        ).andExpect(status().isOk()).andReturn();

        final WarpDTO warpDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);

        assertEquals(warpCreationRequest.getName(), warpDTO.getName());
        assertEquals(warpCreationRequest.getJsonFormattedDescription(), warpDTO.getJsonFormattedDescription());
        assertEquals(warpCreationRequest.getWarpItem(), warpDTO.getWarpItem());
        assertEquals(warpCreationRequest.getPlayerOwnerUuid(), warpDTO.getPlayerOwnerUuid());
        assertEquals(warpCreationRequest.getType(), warpDTO.getType());
        assertEquals(0, warpDTO.getUses());
        assertEquals(0, warpDTO.getLikes());
        assertNotNull(warpDTO.getId());
        assertNotNull(warpDTO.getConfig().getWarp().getId());
        assertNull(warpDTO.getConfig().getWarp().getConfig());

        final WarpConfigDTO defaultConfig = WarpConfigDTO.initWithDefaults(warpDTO);
        defaultConfig.setId(warpDTO.getConfig().getId());
        defaultConfig.setCreatedAt(warpDTO.getConfig().getCreatedAt());
        defaultConfig.setUpdatedAt(warpDTO.getConfig().getUpdatedAt());
        assertEquals(defaultConfig, warpDTO.getConfig());
        assertEquals(defaultConfig.hashCode(), warpDTO.getConfig().hashCode());

        assertNotEquals(warpCreationRequest, warpDTO);
        warpCreationRequest.setConfig(warpDTO.getConfig());
        warpCreationRequest.setId(warpDTO.getId());
        warpCreationRequest.setCreatedAt(warpDTO.getCreatedAt());
        warpCreationRequest.setUpdatedAt(warpDTO.getUpdatedAt());
        assertEquals(warpCreationRequest, warpDTO);

        warpDTO.setName(UUID.randomUUID().toString());
        warpDTO.setJsonFormattedDescription(UUID.randomUUID().toString());

        result = mockMvc.perform(
                put("/" + WarpClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(warpDTO))
        ).andExpect(status().isOk()).andReturn();

        final WarpDTO updatedWarpDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
        assertEquals(warpDTO.getName(), updatedWarpDTO.getName());
        assertEquals(warpDTO.getJsonFormattedDescription(), updatedWarpDTO.getJsonFormattedDescription());
        assertEquals(warpDTO.getWarpItem(), updatedWarpDTO.getWarpItem());

        assertTrue(warpConfigRepository.findByUuid(updatedWarpDTO.getConfig().getId().toString()).isPresent());

        final WarpDTO patchRequest = new WarpDTO();
        patchRequest.setId(updatedWarpDTO.getId());
        patchRequest.setName("PATCH-" + UUID.randomUUID());
        result = mockMvc.perform(
                patch("/" + WarpClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(patchRequest))
        ).andExpect(status().isOk()).andReturn();
        final WarpDTO patchedWarpDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
        assertEquals(updatedWarpDTO.getId(), patchedWarpDTO.getId());
        assertNotEquals(updatedWarpDTO.getName(), patchedWarpDTO.getName());
        assertEquals(patchRequest.getName(), patchedWarpDTO.getName());
        assertNotNull(patchedWarpDTO.getUpdatedAt());
        assertNotNull(patchedWarpDTO.getConfig());
        assertEquals(updatedWarpDTO.getConfig(), patchedWarpDTO.getConfig());

        mockMvc.perform(delete("/" + WarpClientImpl.PATH + "?id=" + updatedWarpDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/" + WarpClientImpl.PATH + "?id=" + updatedWarpDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());

        assertFalse(warpConfigRepository.findByUuid(updatedWarpDTO.getConfig().getId().toString()).isPresent());
    }

    @Test
    void testUpdateConfig() throws Exception {
        final WarpDTO warpCreationRequest = new WarpDTO(
                UUID.randomUUID().toString(),
                "test",
                "STONE",
                UUID.randomUUID(),
                WarpType.PLAYER_WARP
        );
        LocationDTO.fillWithRandomValuesForTestingPurposes(warpCreationRequest);

        MvcResult result = mockMvc.perform(
                post("/" + WarpClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(warpCreationRequest))
        ).andExpect(status().isOk()).andReturn();

        WarpDTO warpDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
        WarpConfigDTO createdConfig = warpDTO.getConfig();
        createdConfig.setWarp(null);

        mockMvc.perform(put("/" + WarpClientImpl.PATH + "/config")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(createdConfig))
        ).andExpect(status().isBadRequest());

        warpDTO.setId(null);
        createdConfig.setWarp(warpDTO);

        mockMvc.perform(put("/" + WarpClientImpl.PATH + "/config")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(createdConfig))
        ).andExpect(status().isBadRequest());

        warpDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
        createdConfig = warpDTO.getConfig();

        createdConfig.setPrice(10.0);
        createdConfig.setPublicAccess(!createdConfig.getPublicAccess());

        result = mockMvc.perform(put("/" + WarpClientImpl.PATH + "/config")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(createdConfig))
        ).andExpect(status().isOk()).andReturn();

        final WarpConfigDTO updatedConfig = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpConfigDTO.class);
        assertEquals(warpDTO.getConfig().getWarp().getId(), updatedConfig.getWarp().getId());
        assertEquals(createdConfig.getPrice(), updatedConfig.getPrice());
        assertEquals(createdConfig.getPublicAccess(), updatedConfig.getPublicAccess());

        result = mockMvc.perform(get("/" + WarpClientImpl.PATH + "/" + updatedConfig.getWarp().getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                ).andExpect(status().isOk()).andReturn();

        final WarpDTO checkGetWarpFromConfig = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
        assertEquals(updatedConfig.getWarp().getId(), checkGetWarpFromConfig.getId());
        assertEquals(updatedConfig, checkGetWarpFromConfig.getConfig());
    }
}
