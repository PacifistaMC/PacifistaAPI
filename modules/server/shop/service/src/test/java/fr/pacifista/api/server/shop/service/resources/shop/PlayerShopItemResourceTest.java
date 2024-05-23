package fr.pacifista.api.server.shop.service.resources.shop;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.shop.client.clients.shop.PlayerShopItemImplClient;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerShopItemResourceTest {

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
        final PlayerShopItemDTO playerShopItemDTO = new PlayerShopItemDTO(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Random().nextDouble(1000)
        );

        MvcResult mvcResult = this.mockMvc.perform(post("/" + PlayerShopItemImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(playerShopItemDTO)))
                .andExpect(status().isOk()).andReturn();
        final PlayerShopItemDTO created = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PlayerShopItemDTO.class);
        assertEquals(playerShopItemDTO.getMinecraftUsername(), created.getMinecraftUsername());
        assertEquals(playerShopItemDTO.getMinecraftUuid(), created.getMinecraftUuid());
        assertEquals(playerShopItemDTO.getItemSerialized(), created.getItemSerialized());
        assertEquals(playerShopItemDTO.getPrice(), created.getPrice());
        assertNull(created.getSoldAt());

        created.setPrice(created.getPrice() + 100);
        created.setSoldAt(new Date());
        mvcResult = this.mockMvc.perform(patch("/" + PlayerShopItemImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(created)))
                .andExpect(status().isOk()).andReturn();
        final PlayerShopItemDTO updated = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PlayerShopItemDTO.class);
        assertEquals(created.getMinecraftUsername(), updated.getMinecraftUsername());
        assertEquals(created.getPrice(), updated.getPrice());
        assertEquals(created.getSoldAt(), updated.getSoldAt());

        mockMvc.perform(delete("/" + PlayerShopItemImplClient.PATH + "?id=" + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID()))
                .andExpect(status().isOk());
    }

}
