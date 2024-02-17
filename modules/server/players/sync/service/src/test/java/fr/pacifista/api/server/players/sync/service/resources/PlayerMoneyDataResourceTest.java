package fr.pacifista.api.server.players.sync.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerMoneyDataResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setupMocks() {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID());
        userDTO.setUsername("test");
        userDTO.setEmail(UUID.randomUUID() + "@test.com");
        userDTO.setRole(UserRole.ADMIN);

        when(userAuthClient.current(any())).thenReturn(userDTO);
    }

    @Test
    void testPatchMultiple() throws Exception {
        List<PlayerMoneyDataDTO> toCreate = List.of(createPlayerMoneyDataDTO(), createPlayerMoneyDataDTO(), createPlayerMoneyDataDTO());
        Type listType = new TypeToken<List<PlayerMoneyDataDTO>>() {}.getType();

        MvcResult result = mockMvc.perform(post("/playersync/money/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(toCreate)))
                .andExpect(status().isOk())
                .andReturn();
        toCreate = jsonHelper.fromJson(result.getResponse().getContentAsString(), listType);

        mockMvc.perform(patch("/playersync/money/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(toCreate)))
                .andExpect(status().isOk());
    }

    private PlayerMoneyDataDTO createPlayerMoneyDataDTO() {
        final PlayerMoneyDataDTO playerMoneyDataDTO = new PlayerMoneyDataDTO();

        playerMoneyDataDTO.setMoney(100.0);
        playerMoneyDataDTO.setOfflineMoney(100.0);
        playerMoneyDataDTO.setPlayerOwnerUuid(UUID.randomUUID());
        return playerMoneyDataDTO;
    }

}
