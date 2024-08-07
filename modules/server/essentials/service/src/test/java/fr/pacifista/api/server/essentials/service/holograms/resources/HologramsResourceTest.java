package fr.pacifista.api.server.essentials.service.holograms.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.holograms.clients.HologramClientImpl;
import fr.pacifista.api.server.essentials.client.holograms.dtos.HologramDTO;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HologramsResourceTest {

    private final String route = "/" + HologramClientImpl.PATH;

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
        final HologramDTO parentHologram = new HologramDTO(
                "text",
                ServerType.SURVIE_ALPHA,
                UUID.randomUUID(),
                10.0,
                7.0,
                5.0
        );

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(parentHologram)))
                .andExpect(status().isOk())
                .andReturn();

        final HologramDTO parentHologramCreated = jsonHelper.fromJson(result.getResponse().getContentAsString(), HologramDTO.class);
        assertEquals(parentHologram.getTextSerialized(), parentHologramCreated.getTextSerialized());
        assertEquals(parentHologram.getServerType(), parentHologramCreated.getServerType());
        assertEquals(parentHologram.getWorldUuid(), parentHologramCreated.getWorldUuid());
        assertEquals(parentHologram.getX(), parentHologramCreated.getX());
        assertEquals(parentHologram.getY(), parentHologramCreated.getY());
        assertEquals(parentHologram.getZ(), parentHologramCreated.getZ());
        assertNull(parentHologramCreated.getParentHologram());
        assertTrue(parentHologramCreated.getChildHolograms().isEmpty());

        result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(new HologramDTO(
                                "textChild",
                                parentHologramCreated
                        ))))
                .andExpect(status().isOk())
                .andReturn();
        final HologramDTO childHologram1Created = jsonHelper.fromJson(result.getResponse().getContentAsString(), HologramDTO.class);
        assertEquals(parentHologramCreated.getServerType(), childHologram1Created.getServerType());
        assertEquals(parentHologramCreated.getWorldUuid(), childHologram1Created.getWorldUuid());
        assertEquals(parentHologramCreated.getX(), childHologram1Created.getX());
        assertNotEquals(parentHologramCreated.getY(), childHologram1Created.getY());
        assertEquals(parentHologramCreated.getZ(), childHologram1Created.getZ());
        assertEquals(parentHologramCreated, childHologram1Created.getParentHologram());

        mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(new HologramDTO(
                                "textChildCantCreateFromChild",
                                childHologram1Created
                        ))))
                .andExpect(status().isBadRequest());

        result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(new HologramDTO(
                                "textChild2",
                                parentHologramCreated
                        ))))
                .andExpect(status().isOk())
                .andReturn();
        final HologramDTO childHologram2Created = jsonHelper.fromJson(result.getResponse().getContentAsString(), HologramDTO.class);

        result = mockMvc.perform(get(route + "/" + parentHologramCreated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andReturn();
        final HologramDTO parentHologramRetrieved = jsonHelper.fromJson(result.getResponse().getContentAsString(), HologramDTO.class);
        assertEquals(parentHologramCreated, parentHologramRetrieved);
        assertEquals(2, parentHologramRetrieved.getChildHolograms().size());
        final List<HologramDTO> childHolograms = parentHologramRetrieved.getChildHolograms();
        assertEquals(childHologram1Created, childHolograms.get(0));
        assertEquals(childHologram2Created, childHolograms.get(1));
        mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(parentHologram)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(parentHologram)))
                .andExpect(status().isBadRequest());


    }

}
