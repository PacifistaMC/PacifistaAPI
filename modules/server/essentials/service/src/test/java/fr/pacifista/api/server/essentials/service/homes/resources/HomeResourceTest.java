package fr.pacifista.api.server.essentials.service.homes.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.homes.clients.HomeClientImpl;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeResourceTest {

    private final String route = "/" + HomeClientImpl.PATH;

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
        final HomeDTO homeDTO = new HomeDTO();

        homeDTO.setName("homeName");
        homeDTO.setPlayerUuid(UUID.randomUUID());
        homeDTO.setWorldUuid(UUID.randomUUID());
        homeDTO.setX(0.0);
        homeDTO.setY(0.0);
        homeDTO.setZ(0.0);
        homeDTO.setYaw(0.0f);
        homeDTO.setPitch(0.0f);
        homeDTO.setServerType(ServerType.CREATIVE);

        MvcResult result = mockMvc.perform(post(route)
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(homeDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final HomeDTO response = jsonHelper.fromJson(result.getResponse().getContentAsString(), HomeDTO.class);
        assertEquals(homeDTO.getName(), response.getName());
        response.setName("homeName2");

        result = mockMvc.perform(patch(route)
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(response.getName(), jsonHelper.fromJson(result.getResponse().getContentAsString(), HomeDTO.class).getName());
    }

}
