package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.claim.client.clients.ClaimDataClientImpl;
import fr.pacifista.api.server.claim.client.clients.ClaimDataConfigClientImpl;
import fr.pacifista.api.server.claim.client.clients.ClaimDataUserClientImpl;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import fr.pacifista.api.server.claim.client.enums.ClaimDataUserRole;
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
class ClaimDataResourceTest {

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
        final ClaimDataDTO response = createClaimDataDTO(new ClaimDataDTO(
                ServerType.SURVIE_ALPHA,
                UUID.randomUUID(),
                10.0,
                20.0,
                30.0,
                40.0
        ));

        assertNull(response.getParent());
        response.setWorldId(UUID.randomUUID().toString());
        final ClaimDataDTO updatedResponse = updateClaim(response);
        assertEquals(response.getWorldId(), updatedResponse.getWorldId());

        updatedResponse.getConfig().setAnimalProtection(!updatedResponse.getConfig().getAnimalProtection());
        final ClaimDataConfigDTO updatedConfigResponse = updateClaimConfig(updatedResponse.getConfig());
        assertEquals(updatedResponse.getConfig().getAnimalProtection(), updatedConfigResponse.getAnimalProtection());

        final ClaimDataDTO foundResponse = findClaimById(updatedResponse.getId());
        assertEquals(updatedResponse.getId(), foundResponse.getId());
        assertEquals(updatedResponse.getWorldId(), foundResponse.getWorldId());
        assertEquals(updatedConfigResponse.getAnimalProtection(), foundResponse.getConfig().getAnimalProtection());

        final ClaimDataUserDTO claimDataUserDTO = new ClaimDataUserDTO(
                UUID.randomUUID(),
                foundResponse,
                ClaimDataUserRole.MANAGER
        );
        final ClaimDataUserDTO createdClaimDataUserDTO = createClaimDataUserDTO(claimDataUserDTO);
        createdClaimDataUserDTO.setRole(ClaimDataUserRole.MEMBER);
        final ClaimDataUserDTO updatedClaimDataUserDTO = updateClaimUser(createdClaimDataUserDTO);
        assertEquals(createdClaimDataUserDTO.getRole(), updatedClaimDataUserDTO.getRole());

        final ClaimDataDTO foundResponseAfterUserCreation = findClaimById(updatedResponse.getId());
        assertTrue(foundResponseAfterUserCreation.getUsers().contains(updatedClaimDataUserDTO));

        final ClaimDataDTO childClaim = createClaimDataDTO(new ClaimDataDTO(
                foundResponseAfterUserCreation,
                ServerType.SURVIE_ALPHA,
                UUID.randomUUID(),
                10.0,
                20.0,
                30.0,
                40.0
        ));
        assertEquals(childClaim.getParent(), foundResponseAfterUserCreation);

        deleteClaim(updatedResponse);
        checkIfClaimIsDeleted(foundResponseAfterUserCreation);
        checkIfClaimConfigIsDeleted(updatedConfigResponse);
        checkIfClaimUserIsDeleted(updatedClaimDataUserDTO);
    }

    private ClaimDataDTO createClaimDataDTO(final ClaimDataDTO claimDataDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/" + ClaimDataClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimDataDTO)))
                .andExpect(status().isOk()).andReturn();

        final ClaimDataDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataDTO.class);
        assertEquals(claimDataDTO.getServerType(), response.getServerType());
        assertEquals(claimDataDTO.getWorldId(), response.getWorldId());
        assertEquals(claimDataDTO.getLesserBoundaryCornerX(), response.getLesserBoundaryCornerX());
        assertEquals(claimDataDTO.getLesserBoundaryCornerZ(), response.getLesserBoundaryCornerZ());
        assertEquals(claimDataDTO.getGreaterBoundaryCornerX(), response.getGreaterBoundaryCornerX());
        assertEquals(claimDataDTO.getGreaterBoundaryCornerZ(), response.getGreaterBoundaryCornerZ());
        assertNotNull(response.getId());
        assertNotNull(response.getConfig());

        return response;
    }

    private ClaimDataDTO updateClaim(final ClaimDataDTO claimDataDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/" + ClaimDataClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimDataDTO)))
                .andExpect(status().isOk()).andReturn();

        final ClaimDataDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataDTO.class);
        assertEquals(claimDataDTO.getServerType(), response.getServerType());
        assertEquals(claimDataDTO.getWorldId(), response.getWorldId());
        assertEquals(claimDataDTO.getLesserBoundaryCornerX(), response.getLesserBoundaryCornerX());
        assertEquals(claimDataDTO.getLesserBoundaryCornerZ(), response.getLesserBoundaryCornerZ());
        assertEquals(claimDataDTO.getGreaterBoundaryCornerX(), response.getGreaterBoundaryCornerX());
        assertEquals(claimDataDTO.getGreaterBoundaryCornerZ(), response.getGreaterBoundaryCornerZ());
        assertNotNull(response.getId());
        assertNotNull(response.getConfig());

        return response;
    }

    private ClaimDataConfigDTO updateClaimConfig(final ClaimDataConfigDTO claimDataConfigDTO) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(patch("/" + ClaimDataConfigClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimDataConfigDTO)))
                .andExpect(status().isOk()).andReturn();

        final ClaimDataConfigDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataConfigDTO.class);
        assertEquals(claimDataConfigDTO.getClaimDataId(), response.getClaimDataId());
        assertEquals(claimDataConfigDTO.getExplosionEnabled(), response.getExplosionEnabled());
        assertEquals(claimDataConfigDTO.getFireSpreadEnabled(), response.getFireSpreadEnabled());
        assertEquals(claimDataConfigDTO.getMobGriefingEnabled(), response.getMobGriefingEnabled());
        assertEquals(claimDataConfigDTO.getPvpEnabled(), response.getPvpEnabled());
        assertEquals(claimDataConfigDTO.getPublicAccess(), response.getPublicAccess());
        assertEquals(claimDataConfigDTO.getPublicInteractButtons(), response.getPublicInteractButtons());
        assertEquals(claimDataConfigDTO.getPublicInteractDoorsTrapDoors(), response.getPublicInteractDoorsTrapDoors());
        assertEquals(claimDataConfigDTO.getPublicInteractChests(), response.getPublicInteractChests());
        assertEquals(claimDataConfigDTO.getAnimalProtection(), response.getAnimalProtection());
        assertEquals(claimDataConfigDTO.getGriefProtection(), response.getGriefProtection());

        return response;
    }

    private ClaimDataDTO findClaimById(final UUID id) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/" + ClaimDataClientImpl.PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk()).andReturn();

        return jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataDTO.class);
    }

    private ClaimDataUserDTO createClaimDataUserDTO(final ClaimDataUserDTO claimDataUserDTO) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/" + ClaimDataUserClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimDataUserDTO)))
                .andExpect(status().isOk()).andReturn();

        final ClaimDataUserDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataUserDTO.class);
        assertEquals(claimDataUserDTO.getPlayerId(), response.getPlayerId());
        assertEquals(claimDataUserDTO.getClaimDataId(), response.getClaimDataId());
        assertEquals(claimDataUserDTO.getRole(), response.getRole());

        return response;
    }

    private ClaimDataUserDTO updateClaimUser(final ClaimDataUserDTO claimDataUserDTO) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(patch("/" + ClaimDataUserClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(claimDataUserDTO)))
                .andExpect(status().isOk()).andReturn();

        final ClaimDataUserDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ClaimDataUserDTO.class);
        assertEquals(claimDataUserDTO.getPlayerId(), response.getPlayerId());
        assertEquals(claimDataUserDTO.getClaimDataId(), response.getClaimDataId());
        assertEquals(claimDataUserDTO.getRole(), response.getRole());

        return response;
    }

    private void deleteClaim(final ClaimDataDTO claimDataDTO) throws Exception {
        mockMvc.perform(delete("/" + ClaimDataClientImpl.PATH + "?id=" + claimDataDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
    }

    private void checkIfClaimIsDeleted(final ClaimDataDTO claimDataDTO) throws Exception {
        mockMvc.perform(get("/" + ClaimDataClientImpl.PATH + "/" + claimDataDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

    private void checkIfClaimConfigIsDeleted(final ClaimDataConfigDTO claimDataConfigDTO) throws Exception {
        mockMvc.perform(get("/" + ClaimDataConfigClientImpl.PATH + "/" + claimDataConfigDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

    private void checkIfClaimUserIsDeleted(final ClaimDataUserDTO claimDataUserDTO) throws Exception {
        mockMvc.perform(get("/" + ClaimDataUserClientImpl.PATH + "/" + claimDataUserDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

}
