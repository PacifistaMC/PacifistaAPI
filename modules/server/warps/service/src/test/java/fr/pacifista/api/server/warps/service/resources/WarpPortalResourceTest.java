package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.warps.client.clients.WarpClientImpl;
import fr.pacifista.api.server.warps.client.clients.WarpPortalClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import fr.pacifista.api.server.warps.client.enums.WarpType;
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
class WarpPortalResourceTest {

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
        final WarpPortalDTO createPortalRequest = new WarpPortalDTO(
                this.createWarp(),
                ServerType.HUB,
                UUID.randomUUID(),
                10,
                11,
                10,
                20,
                21,
                23
        );

        MvcResult result = mockMvc.perform(
                post("/" + WarpPortalClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(createPortalRequest))
        ).andExpect(status().isOk()).andReturn();

        final WarpPortalDTO warpPortalDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpPortalDTO.class);

        assertEquals(createPortalRequest.getWarp(), warpPortalDTO.getWarp());
        assertEquals(createPortalRequest.getServerType(), warpPortalDTO.getServerType());
        assertEquals(createPortalRequest.getWorldId(), warpPortalDTO.getWorldId());
        assertEquals(createPortalRequest.getLesserBoundaryCornerX(), warpPortalDTO.getLesserBoundaryCornerX());
        assertEquals(createPortalRequest.getLesserBoundaryCornerY(), warpPortalDTO.getLesserBoundaryCornerY());
        assertEquals(createPortalRequest.getLesserBoundaryCornerZ(), warpPortalDTO.getLesserBoundaryCornerZ());
        assertEquals(createPortalRequest.getGreaterBoundaryCornerX(), warpPortalDTO.getGreaterBoundaryCornerX());
        assertEquals(createPortalRequest.getGreaterBoundaryCornerY(), warpPortalDTO.getGreaterBoundaryCornerY());
        assertEquals(createPortalRequest.getGreaterBoundaryCornerZ(), warpPortalDTO.getGreaterBoundaryCornerZ());

        warpPortalDTO.setServerType(ServerType.SURVIE_RESOURCE);

        result = mockMvc.perform(
                put("/" + WarpPortalClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(warpPortalDTO))
        ).andExpect(status().isOk()).andReturn();

        final WarpPortalDTO updatedWarpPortalDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpPortalDTO.class);

        assertEquals(warpPortalDTO, updatedWarpPortalDTO);
        assertEquals(warpPortalDTO.hashCode(), updatedWarpPortalDTO.hashCode());
        assertEquals(warpPortalDTO.getWarp(), updatedWarpPortalDTO.getWarp());
        assertEquals(warpPortalDTO.getServerType(), updatedWarpPortalDTO.getServerType());

        mockMvc.perform(delete("/" + WarpPortalClientImpl.PATH + "?id=" + updatedWarpPortalDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        mockMvc.perform(delete("/" + WarpPortalClientImpl.PATH + "?id=" + updatedWarpPortalDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound());
    }

    @Test
    void checkPortalRemovedOnWarpDeletion() throws Exception {
        final WarpDTO warpDTO = this.createWarp();

        MvcResult result = mockMvc.perform(
                post("/" + WarpPortalClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(new WarpPortalDTO(
                                warpDTO,
                                ServerType.HUB,
                                UUID.randomUUID(),
                                10,
                                11,
                                10,
                                20,
                                21,
                                23
                        )))
        ).andExpect(status().isOk()).andReturn();
        final WarpPortalDTO warpPortalDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpPortalDTO.class);

        mockMvc.perform(delete("/" + WarpClientImpl.PATH + "?id=" + warpDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());
        mockMvc.perform(delete("/" + WarpClientImpl.PATH + "?id=" + warpDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound());

        mockMvc.perform(get("/" + WarpPortalClientImpl.PATH + "/" + warpPortalDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound());
    }

    private WarpDTO createWarp() throws Exception {
        final WarpDTO warpDTO = new WarpDTO(
                UUID.randomUUID().toString(),
                "desc",
                "STONE",
                UUID.randomUUID(),
                WarpType.PLAYER_WARP
        );
        LocationDTO.fillWithRandomValuesForTestingPurposes(warpDTO);

        final MvcResult result = mockMvc.perform(
                post("/" + WarpClientImpl.PATH)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(warpDTO))
        ).andExpect(status().isOk()).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
    }

}
