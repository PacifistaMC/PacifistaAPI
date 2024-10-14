package fr.pacifista.api.server.warps.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.server.warps.client.clients.WarpClientImpl;
import fr.pacifista.api.server.warps.client.clients.WarpPlayerInteractionClientImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.client.enums.WarpInteractionType;
import fr.pacifista.api.server.warps.client.enums.WarpType;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
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
class WarpPlayerInteractionResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private WarpPlayerInteractionRepository warpPlayerInteractionRepository;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);

        warpPlayerInteractionRepository.deleteAll();
    }

    @Test
    void testCrudLikes() throws Exception {
        WarpDTO warp = this.createWarp();
        final UUID playerId = UUID.randomUUID();

        final WarpDTO emptyWarp = new WarpDTO();
        emptyWarp.setId(UUID.randomUUID());
        final WarpPlayerInteractionDTO request = new WarpPlayerInteractionDTO(
                emptyWarp,
                playerId,
                WarpInteractionType.LIKE
        );

        mockMvc.perform(post("/" + WarpPlayerInteractionClientImpl.PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(request))
        ).andExpect(status().isNotFound());

        request.setInteractionType(WarpInteractionType.USE);
        mockMvc.perform(post("/" + WarpPlayerInteractionClientImpl.PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(request))
        ).andExpect(status().isNotFound());

        final WarpPlayerInteractionDTO like = this.like(warp, playerId);
        assertEquals(warp.getId(), like.getWarp().getId());
        assertEquals(playerId, like.getPlayerId());
        assertEquals(WarpInteractionType.LIKE, like.getInteractionType());

        warp = this.getWarp(warp.getId());
        assertEquals(1, warp.getLikes());
        assertEquals(0, warp.getUses());

        mockMvc.perform(post("/" + WarpPlayerInteractionClientImpl.PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new WarpPlayerInteractionDTO(
                        warp,
                        playerId,
                        WarpInteractionType.LIKE
                )))
        ).andExpect(status().isBadRequest());

        this.like(warp, UUID.randomUUID());
        warp = this.getWarp(warp.getId());
        assertEquals(2, warp.getLikes());
        assertEquals(0, warp.getUses());

        mockMvc.perform(delete("/" + WarpPlayerInteractionClientImpl.PATH + "?id=" + like.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        warp = this.getWarp(warp.getId());
        assertEquals(1, warp.getLikes());
        assertEquals(0, warp.getUses());



        final WarpPlayerInteractionDTO use = this.use(warp, playerId);
        assertEquals(warp.getId(), use.getWarp().getId());
        assertEquals(playerId, use.getPlayerId());
        assertEquals(WarpInteractionType.USE, use.getInteractionType());

        warp = this.getWarp(warp.getId());
        assertEquals(1, warp.getLikes());
        assertEquals(1, warp.getUses());

        mockMvc.perform(post("/" + WarpPlayerInteractionClientImpl.PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new WarpPlayerInteractionDTO(
                        warp,
                        playerId,
                        WarpInteractionType.USE
                )))
        ).andExpect(status().isOk());

        this.use(warp, UUID.randomUUID());
        warp = this.getWarp(warp.getId());
        assertEquals(1, warp.getLikes());
        assertEquals(3, warp.getUses());

        mockMvc.perform(delete("/" + WarpPlayerInteractionClientImpl.PATH + "?id=" + use.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        warp = this.getWarp(warp.getId());
        assertEquals(1, warp.getLikes());
        assertEquals(2, warp.getUses());
    }

    @Test
    void testInteractionDeletedOnWarpRemoved() throws Exception {
        final WarpDTO warp = this.createWarp();
        final UUID playerId = UUID.randomUUID();

        final WarpPlayerInteractionDTO like = this.like(warp, playerId);
        final WarpPlayerInteractionDTO use = this.use(warp, playerId);

        mockMvc.perform(delete("/" + WarpClientImpl.PATH + "?id=" + warp.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isOk());

        mockMvc.perform(get("/" + WarpPlayerInteractionClientImpl.PATH + "/" + like.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound());

        mockMvc.perform(get("/" + WarpPlayerInteractionClientImpl.PATH + "/" + use.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
        ).andExpect(status().isNotFound());
    }

    private WarpPlayerInteractionDTO like(final WarpDTO warp, final UUID playerId) throws Exception {
        return this.create(warp, playerId, WarpInteractionType.LIKE);
    }

    private WarpPlayerInteractionDTO use(final WarpDTO warp, final UUID playerId) throws Exception {
        return this.create(warp, playerId, WarpInteractionType.USE);
    }

    private WarpPlayerInteractionDTO create(final WarpDTO warp, final UUID playerId, final WarpInteractionType interactionType) throws Exception {
        final WarpPlayerInteractionDTO request = new WarpPlayerInteractionDTO(
                warp,
                playerId,
                interactionType
        );

        final MvcResult result = mockMvc.perform(post("/" + WarpPlayerInteractionClientImpl.PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(request))
        ).andExpect(status().isOk()).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpPlayerInteractionDTO.class);
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

    private WarpDTO getWarp(final UUID id) throws Exception {
        final MvcResult result = mockMvc.perform(get("/" + WarpClientImpl.PATH + "/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), WarpDTO.class);
    }

}
