package fr.pacifista.api.web.shop.service.categories.resources;

import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ShopCategoryResourceTest extends ResourceTestHandler {

    @MockitoBean
    ShopCategoryService categoryService;

    private final String route = "/web/shop/categories";

    @MockitoBean
    PacifistaPlusService pacifistaPlusService;

    @BeforeEach
    void mockPacifistaPlusService() {
        when(pacifistaPlusService.getPlan()).thenReturn(new PaypalPlanDTO());
    }

    @BeforeEach
    void setupMocks() {
        reset(categoryService);
        when(categoryService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(categoryService.create(any(ShopCategoryDTO.class))).thenReturn(new ShopCategoryDTO());
        when(categoryService.update(any(ShopCategoryDTO.class))).thenReturn(new ShopCategoryDTO());
        doNothing().when(categoryService).delete(anyString());
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @Test
    void testGetNoTokenSuccess() throws Exception {
        super.setupNormal();

        mockMvc.perform(get(this.route)).andExpect(status().isOk());
    }

    @Test
    void testGetModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(get(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateAdmin() throws Exception {
        super.setupAdmin();

        mockMvc.perform(post(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testCreatePacifistaAdminFail() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(post(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testPatchAdmin() throws Exception {
        super.setupAdmin();

        mockMvc.perform(patch(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testDeleteAdmin() throws Exception {
        super.setupAdmin();

        mockMvc.perform(delete(this.route + "?id=" + UUID.randomUUID())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateModoRefused() throws Exception {
        super.setupModerator();

        mockMvc.perform(post(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testCreateUserRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(post(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isForbidden());
    }

    public ShopCategoryDTO generateDTO() {
        final Random random = new Random();
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName("ddd" + random.nextInt(10));
        shopCategoryDTO.setDescription(UUID.randomUUID().toString());
        shopCategoryDTO.setMultiPurchaseAllowed(false);

        return shopCategoryDTO;
    }
}
