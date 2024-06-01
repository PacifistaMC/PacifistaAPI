package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopCategoryImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopItem;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopPlayerLimit;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopItemRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopPlayerLimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminShopCategoryResourceTest  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private AdminShopItemRepository adminShopItemRepository;

    @Autowired
    private AdminShopPlayerLimitRepository adminShopPlayerLimitRepository;

    @Autowired
    private AdminShopCategoryRepository adminShopCategoryRepository;

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
        final AdminShopCategoryDTO adminShopCategoryDTO = new AdminShopCategoryDTO(UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString());

        MvcResult mvcResult = mockMvc.perform(post("/" + AdminShopCategoryImplClient.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UUID.randomUUID())
                        .content(jsonHelper.toJson(adminShopCategoryDTO)))
                        .andExpect(status().isOk())
                        .andReturn();
        final AdminShopCategoryDTO createdAdminShopCategoryDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopCategoryDTO.class);
        assertEquals(adminShopCategoryDTO.getName(), createdAdminShopCategoryDTO.getName());
        assertEquals(adminShopCategoryDTO.getMoneySellLimit(), createdAdminShopCategoryDTO.getMoneySellLimit());

        createdAdminShopCategoryDTO.setMoneySellLimit(20.0);
        mvcResult = mockMvc.perform(patch("/" + AdminShopCategoryImplClient.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UUID.randomUUID())
                        .content(jsonHelper.toJson(createdAdminShopCategoryDTO)))
                        .andExpect(status().isOk())
                        .andReturn();
        final AdminShopCategoryDTO updatedAdminShopCategoryDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), AdminShopCategoryDTO.class);
        assertEquals(adminShopCategoryDTO.getName(), updatedAdminShopCategoryDTO.getName());
        assertEquals(createdAdminShopCategoryDTO.getMoneySellLimit(), updatedAdminShopCategoryDTO.getMoneySellLimit());

        final AdminShopItem adminShopItem = generateItem(updatedAdminShopCategoryDTO);
        final AdminShopPlayerLimit adminShopPlayerLimit = generatePlayerLimit(updatedAdminShopCategoryDTO);

        mockMvc.perform(delete("/" + AdminShopCategoryImplClient.PATH + "?id=" + createdAdminShopCategoryDTO.getId())
                        .header("Authorization", "Bearer " + UUID.randomUUID()))
                .andExpect(status().isOk());

        assertTrue(adminShopItemRepository.findById(adminShopItem.getId()).isEmpty());
        assertTrue(adminShopPlayerLimitRepository.findById(adminShopPlayerLimit.getId()).isEmpty());
    }

    private AdminShopItem generateItem(final AdminShopCategoryDTO category) throws Exception {
        final AdminShopItem adminShopItem = new AdminShopItem();
        adminShopItem.setCategory(this.adminShopCategoryRepository.findByUuid(category.getId().toString()).orElseThrow(() -> new Exception("Category not found")));
        adminShopItem.setMaterial(UUID.randomUUID().toString());
        adminShopItem.setPrice(10.0);

        return this.adminShopItemRepository.save(adminShopItem);
    }

    private AdminShopPlayerLimit generatePlayerLimit(final AdminShopCategoryDTO category) {
        final AdminShopPlayerLimit adminShopPlayerLimit = new AdminShopPlayerLimit();
        adminShopPlayerLimit.setCategory(this.adminShopCategoryRepository.findByUuid(category.getId().toString()).orElseThrow(() -> new RuntimeException("Category not found")));
        adminShopPlayerLimit.setPlayerId(UUID.randomUUID());
        adminShopPlayerLimit.setMoneyGenerated(10.0);

        return this.adminShopPlayerLimitRepository.save(adminShopPlayerLimit);
    }

}
