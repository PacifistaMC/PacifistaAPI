package fr.pacifista.api.server.claim.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.claim.client.clients.UserClaimAmountClientImpl;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;
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
class UserClaimAmountResourceTest {

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
        final UserClaimAmountDTO userClaimAmountDTO = new UserClaimAmountDTO(UUID.randomUUID(), 100);

        MvcResult mvcResult = mockMvc.perform(post("/" + UserClaimAmountClientImpl.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(userClaimAmountDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final UserClaimAmountDTO response = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), UserClaimAmountDTO.class);
        assertEquals(userClaimAmountDTO.getPlayerId(), response.getPlayerId());
        assertEquals(userClaimAmountDTO.getBlocksAmount(), response.getBlocksAmount());

        response.setBlocksAmount(200);
        mvcResult = mockMvc.perform(patch("/" + UserClaimAmountClientImpl.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk())
                .andReturn();

        final UserClaimAmountDTO updatedResponse = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), UserClaimAmountDTO.class);
        assertEquals(response, updatedResponse);
        assertEquals(response.getPlayerId(), updatedResponse.getPlayerId());
        assertEquals(response.getBlocksAmount(), updatedResponse.getBlocksAmount());
    }

}
