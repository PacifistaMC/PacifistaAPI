package fr.pacifista.api.web.shop.service.articles.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.funixproductions.core.tools.pdf.tools.VATInformation;
import com.google.gson.reflect.TypeToken;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ShopArticleResourceTest extends ResourceTestHandler {

    @MockitoBean
    ShopArticleService articleService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @MockitoBean
    CurrentSession currentSession;

    private final String route = "/web/shop/articles";

    private final double price = 10;

    @BeforeEach
    void setupMock() {
        final PageDTO<ShopArticleDTO> pageDTO = new PageDTO<>();
        final ShopArticleDTO shopArticleDTO = new ShopArticleDTO();
        shopArticleDTO.setPrice(price);
        pageDTO.setContent(List.of(shopArticleDTO));

        final UserDTO userDTO = new UserDTO();
        userDTO.setCountry(new UserDTO.Country(
                "France", 100, "FR", "FRA"
        ));
        when(currentSession.getCurrentUser()).thenReturn(userDTO);

        reset(articleService);
        when(articleService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(pageDTO);
        when(articleService.findById(anyString())).thenReturn(shopArticleDTO);

        when(articleService.store(any(), any())).thenReturn(new ShopArticleDTO());
        when(articleService.create(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        when(articleService.update(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        doNothing().when(articleService).delete(anyString());
    }

    @Test
    void testGetAllWithPrices() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(this.route)
        ).andExpect(status().isOk()).andReturn();

        final Type type = new TypeToken<PageDTO<ShopArticleDTO>>() {}.getType();
        final PageDTO<ShopArticleDTO> pageDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);

        assertEquals(1, pageDTO.getContent().size());
        final ShopArticleDTO shopArticleDTO = pageDTO.getContent().get(0);
        assertEquals(price, shopArticleDTO.getPrice());
        assertEquals(price * (VATInformation.FRANCE.getVatRate() / 100), shopArticleDTO.getTax());
        assertEquals(price + (price * (VATInformation.FRANCE.getVatRate() / 100)), shopArticleDTO.getPriceWithTax());
    }

    @Test
    void testGetIdWithPrices() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get(this.route + "/" + UUID.randomUUID())
        ).andExpect(status().isOk()).andReturn();

        final ShopArticleDTO shopArticleDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), ShopArticleDTO.class);

        assertEquals(price, shopArticleDTO.getPrice());
        assertEquals(price * (VATInformation.FRANCE.getVatRate() / 100), shopArticleDTO.getTax());
        assertEquals(price + (price * (VATInformation.FRANCE.getVatRate() / 100)), shopArticleDTO.getPriceWithTax());
    }

    @Test
    void testGetNoTokenSuccess() throws Exception {
        super.setupNormal();

        mockMvc.perform(get(this.route)).andExpect(status().isOk());
    }

    @Test
    void testGetTokenSuccess() throws Exception {
        super.setupNormal();

        mockMvc.perform(get(this.route).header("Authorization", "Bearer sdsdfsd")).andExpect(status().isOk());
    }

    @Test
    void testGetModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(get(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreatePacifistaAdminFail() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(multipart(this.route + "/file")
                .file("fileTest", new byte[0])
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(generateDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testCreateAdmin() throws Exception {
        super.setupAdmin();

        final String fileName = "fileNameTest" + UUID.randomUUID();
        final String fileExt = "txt";
        final String fileContent = "test";
        final ShopArticleDTO request = generateDTO();
        final MockMultipartFile file = new MockMultipartFile("file", fileName + "." + fileExt, "text/plain", fileContent.getBytes());

        final MockMultipartFile metadata = new MockMultipartFile(
                "dto",
                "dto",
                MediaType.APPLICATION_JSON_VALUE,
                jsonHelper.toJson(request).getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(multipart(this.route + "/file")
                        .file(file)
                        .file(metadata)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
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
        shopArticleDTO.setCommandExecuted(UUID.randomUUID().toString());

        return shopArticleDTO;
    }
}
