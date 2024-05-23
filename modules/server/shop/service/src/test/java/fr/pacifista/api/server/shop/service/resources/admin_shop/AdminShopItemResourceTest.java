package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopItemImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminShopItemResourceTest extends AdminShopDataWithCategoryTest {

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
        final AdminShopItemDTO adminShopItemDTO = new AdminShopItemDTO(
                UUID.randomUUID().toString(),
                new Random().nextDouble(1000),
                super.generateCategory()
        );

        MvcResult mvcResult = this.mockMvc.perform(post("/" + AdminShopItemImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(adminShopItemDTO)))
                .andExpect(status().isOk())
                .andReturn();
        final AdminShopItemDTO res = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopItemDTO.class);
        assertEquals(adminShopItemDTO.getMaterial(), res.getMaterial());
        assertEquals(adminShopItemDTO.getPrice(), res.getPrice());
        assertEquals(adminShopItemDTO.getCategory(), res.getCategory());

        res.setCategory(super.generateCategory());
        res.setPrice(new Random().nextDouble(1000));
        mvcResult = this.mockMvc.perform(put("/" + AdminShopItemImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(res)))
                .andExpect(status().isOk())
                .andReturn();

        final AdminShopItemDTO updatedRes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopItemDTO.class);
        assertEquals(res.getPrice(), updatedRes.getPrice());
        assertEquals(res.getCategory(), updatedRes.getCategory());

        mockMvc.perform(delete("/" + AdminShopItemImplClient.PATH + "?id=" + res.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(res)))
                .andExpect(status().isOk());
    }

}
