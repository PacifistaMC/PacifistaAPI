package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.server.claim.client.clients.ClaimPhantomPreventionBlockClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClaimPhantomPreventionBlockResource {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Mock
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setMockMvc() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final ClaimPhantomPreventionBlockDTO claimPhantomPreventionBlockDTO = new ClaimPhantomPreventionBlockDTO();
        claimPhantomPreventionBlockDTO.setFuel(10);
        LocationDTO.fillWithRandomValuesForTestingPurposes(claimPhantomPreventionBlockDTO);

        MvcResult result = mockMvc.perform(post("/" + ClaimPhantomPreventionBlockClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimPhantomPreventionBlockDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final ClaimPhantomPreventionBlockDTO createdClaimPhantomPreventionBlockDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), ClaimPhantomPreventionBlockDTO.class);
        assertEquals(claimPhantomPreventionBlockDTO.getFuel(), createdClaimPhantomPreventionBlockDTO.getFuel());
        assertEquals(claimPhantomPreventionBlockDTO.getX(), createdClaimPhantomPreventionBlockDTO.getX());
        assertEquals(claimPhantomPreventionBlockDTO.getY(), createdClaimPhantomPreventionBlockDTO.getY());
        assertEquals(claimPhantomPreventionBlockDTO.getZ(), createdClaimPhantomPreventionBlockDTO.getZ());
        assertEquals(claimPhantomPreventionBlockDTO.getPitch(), createdClaimPhantomPreventionBlockDTO.getPitch());
        assertEquals(claimPhantomPreventionBlockDTO.getYaw(), createdClaimPhantomPreventionBlockDTO.getYaw());
        assertEquals(claimPhantomPreventionBlockDTO.getServerType(), createdClaimPhantomPreventionBlockDTO.getServerType());
        assertEquals(claimPhantomPreventionBlockDTO.getWorldUuid(), createdClaimPhantomPreventionBlockDTO.getWorldUuid());

        createdClaimPhantomPreventionBlockDTO.setFuel(20);
        result = mockMvc.perform(patch("/" + ClaimPhantomPreventionBlockClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(createdClaimPhantomPreventionBlockDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final ClaimPhantomPreventionBlockDTO updatedClaimPhantomPreventionBlockDTO = jsonHelper.fromJson(result.getResponse().getContentAsString(), ClaimPhantomPreventionBlockDTO.class);
        assertEquals(createdClaimPhantomPreventionBlockDTO.getFuel(), updatedClaimPhantomPreventionBlockDTO.getFuel());

        mockMvc.perform(delete("/" + ClaimPhantomPreventionBlockClientImpl.PATH + "?id=" + createdClaimPhantomPreventionBlockDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/" + ClaimPhantomPreventionBlockClientImpl.PATH + "?id=" + createdClaimPhantomPreventionBlockDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

}
