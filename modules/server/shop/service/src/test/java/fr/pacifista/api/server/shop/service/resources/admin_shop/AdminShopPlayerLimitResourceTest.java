package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopPlayerLimitImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopPlayerLimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminShopPlayerLimitResourceTest extends AdminShopDataWithCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @Autowired
    private AdminShopPlayerLimitRepository adminShopPlayerLimitRepository;

    @BeforeEach
    void setupAuth() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);
        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final AdminShopPlayerLimitDTO adminShopPlayerLimitDTO = new AdminShopPlayerLimitDTO(
                UUID.randomUUID(),
                new Random().nextDouble(1000),
                super.generateCategory()
        );

        MvcResult mvcResult = this.mockMvc.perform(post("/" + AdminShopPlayerLimitImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(adminShopPlayerLimitDTO)))
                .andExpect(status().isOk()).andReturn();
        final AdminShopPlayerLimitDTO created = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopPlayerLimitDTO.class);
        assertEquals(adminShopPlayerLimitDTO.getPlayerId(), created.getPlayerId());
        assertEquals(adminShopPlayerLimitDTO.getCategory(), created.getCategory());
        assertEquals(adminShopPlayerLimitDTO.getMoneyGenerated(), created.getMoneyGenerated());

        created.setMoneyGenerated(created.getMoneyGenerated() + 100);
        mvcResult = this.mockMvc.perform(patch("/" + AdminShopPlayerLimitImplClient.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(created)))
                .andExpect(status().isOk()).andReturn();

        final AdminShopPlayerLimitDTO updated = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopPlayerLimitDTO.class);
        assertEquals(created.getPlayerId(), updated.getPlayerId());
        assertEquals(created.getCategory(), updated.getCategory());
        assertEquals(created.getMoneyGenerated(), updated.getMoneyGenerated());

        this.mockMvc.perform(delete("/" + AdminShopPlayerLimitImplClient.PATH + "?id=" + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID())
                .content(jsonHelper.toJson(adminShopPlayerLimitDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testResetPlayerLimits() throws Exception {
        final AdminShopPlayerLimitDTO adminShopPlayerLimitDTO = new AdminShopPlayerLimitDTO(
                UUID.randomUUID(),
                new Random().nextDouble(1000),
                super.generateCategory()
        );

        this.mockMvc.perform(post("/" + AdminShopPlayerLimitImplClient.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UUID.randomUUID())
                        .content(jsonHelper.toJson(adminShopPlayerLimitDTO)))
                .andExpect(status().isOk());

        assertFalse(this.adminShopPlayerLimitRepository.findAll().isEmpty());

        this.mockMvc.perform(delete("/" + AdminShopPlayerLimitImplClient.PATH + "/reset-player-limits")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + UUID.randomUUID()))
                .andExpect(status().isOk());

        assertTrue(this.adminShopPlayerLimitRepository.findAll().isEmpty());
    }
}
