package fr.pacifista.api.server.shop.service.resources.shop;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.dtos.LocationDTO;
import fr.pacifista.api.server.shop.client.clients.shop.PlayerChestShopImplClient;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerChestShopResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setupAuth() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);
        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final PlayerChestShopDTO playerChestShopDTO = new PlayerChestShopDTO(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                new Random().nextDouble(1000)
        );
        LocationDTO.fillWithRandomValuesForTestingPurposes(playerChestShopDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/" + PlayerChestShopImplClient.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UUID.randomUUID())
                        .content(jsonHelper.toJson(playerChestShopDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final PlayerChestShopDTO created = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PlayerChestShopDTO.class);
        assertEquals(playerChestShopDTO.getPlayerId(), created.getPlayerId());
        assertEquals(playerChestShopDTO.getItemSerialized(), created.getItemSerialized());
        assertEquals(playerChestShopDTO.getPrice(), created.getPrice());
        assertNotEquals(playerChestShopDTO, created);
        assertEquals(playerChestShopDTO.getX(), created.getX());

        created.setY(playerChestShopDTO.getY() + 10);
        created.setPrice(created.getPrice() + 100);
        mvcResult = this.mockMvc.perform(patch("/" + PlayerChestShopImplClient.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UUID.randomUUID())
                        .content(jsonHelper.toJson(created)))
                .andExpect(status().isOk())
                .andReturn();
        final PlayerChestShopDTO updated = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PlayerChestShopDTO.class);
        assertEquals(created.getPlayerId(), updated.getPlayerId());
        assertEquals(created.getPrice(), updated.getPrice());
        assertEquals(created.getX(), updated.getX());
        assertEquals(created.getY(), updated.getY());
        assertNotEquals(playerChestShopDTO.getY(), updated.getY());

        mockMvc.perform(delete("/" + PlayerChestShopImplClient.PATH + "?id=" + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID()))
                .andExpect(status().isOk());
    }

}
