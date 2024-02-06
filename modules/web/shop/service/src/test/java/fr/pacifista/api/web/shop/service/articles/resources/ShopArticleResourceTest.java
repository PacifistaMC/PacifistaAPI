package fr.pacifista.api.web.shop.service.articles.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ShopArticleResourceTest extends ResourceTestHandler {

    @MockBean
    ShopArticleService articleService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    String route = "/web";

    @BeforeEach
    void setupMock() {
        this.route = "/web/shop/articles";

        reset(articleService);
        when(articleService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(articleService.store(any(), any())).thenReturn(new ShopArticleDTO());
        when(articleService.create(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        when(articleService.update(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        doNothing().when(articleService).delete(anyString());
    }

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

        mockMvc.perform(multipart(this.route + "/file")
                .file("fileTest", new byte[0])
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isOk());
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

    public ShopArticleDTO generateDTO() {
        final ShopArticleDTO shopArticleDTO = new ShopArticleDTO();
        final ShopCategoryDTO categoryDTO = new ShopCategoryDTO();
        categoryDTO.setId(UUID.randomUUID());
        shopArticleDTO.setCategory(categoryDTO);
        shopArticleDTO.setName(UUID.randomUUID().toString());
        shopArticleDTO.setDescription(UUID.randomUUID().toString());
        shopArticleDTO.setPrice(10.0);
        shopArticleDTO.setHtmlDescription(UUID.randomUUID().toString());
        shopArticleDTO.setLogoUrl(UUID.randomUUID().toString());
        shopArticleDTO.setCommandExecuted(UUID.randomUUID().toString());

        return shopArticleDTO;
    }
}
