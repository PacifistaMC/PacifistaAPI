package fr.pacifista.api.server.essentials.service.pacifista_plus.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.essentials.client.pacifista_plus.clients.PacifistaPlusSubscriptionClientImpl;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
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

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaPlusResourceTest {

    private final String route = "/" + PacifistaPlusSubscriptionClientImpl.PATH;

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
        final PacifistaPlusSubscriptionDTO createSubscriptionDTO = new PacifistaPlusSubscriptionDTO(UUID.randomUUID(), null);

        MvcResult res = mockMvc.perform(post(route)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(createSubscriptionDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final PacifistaPlusSubscriptionDTO createdSubscriptionDTO = jsonHelper.fromJson(res.getResponse().getContentAsString(), PacifistaPlusSubscriptionDTO.class);
        assertEquals(createSubscriptionDTO.getPlayerId(), createdSubscriptionDTO.getPlayerId());
        assertNull(createdSubscriptionDTO.getExpirationDate());
        mockMvc.perform(post(route)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(createSubscriptionDTO)))
                .andExpect(status().isInternalServerError());

        createdSubscriptionDTO.setExpirationDate(new Date());
        res = mockMvc.perform(patch(route)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(createdSubscriptionDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final PacifistaPlusSubscriptionDTO updatedSubscriptionDTO = jsonHelper.fromJson(res.getResponse().getContentAsString(), PacifistaPlusSubscriptionDTO.class);
        assertEquals(createdSubscriptionDTO.getPlayerId(), updatedSubscriptionDTO.getPlayerId());
        assertNotNull(updatedSubscriptionDTO.getExpirationDate());

        mockMvc.perform(delete(route + "?id=" + updatedSubscriptionDTO.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
        mockMvc.perform(delete(route + "?id=" + updatedSubscriptionDTO.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNotFound());
    }

}
