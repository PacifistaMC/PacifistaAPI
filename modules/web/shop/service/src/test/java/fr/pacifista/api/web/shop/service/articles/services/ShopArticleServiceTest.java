package fr.pacifista.api.web.shop.service.articles.services;

import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShopArticleServiceTest {

    @Autowired
    ShopArticleService shopArticleService;

    @Autowired
    ShopCategoryService shopCategoryService;

    @MockitoBean
    CurrentSession currentSession;

    @MockitoBean
    PacifistaPlusService pacifistaPlusService;

    @BeforeEach
    void mockPacifistaPlusService() {
        when(pacifistaPlusService.getPlan()).thenReturn(new PaypalPlanDTO());
    }

    @BeforeEach
    void setupMock() {
        when(currentSession.getCurrentUser()).thenReturn(null);
    }

    @Test
    void createEntity() {
        final ShopCategoryDTO shopCategoryDTO = this.generateCategory();
        final ShopArticleDTO shopArticleDTO = new ShopArticleDTO();
        shopArticleDTO.setCategory(shopCategoryDTO);
        shopArticleDTO.setName(UUID.randomUUID().toString());
        shopArticleDTO.setDescription(UUID.randomUUID().toString());
        shopArticleDTO.setPrice(10.0);
        shopArticleDTO.setHtmlDescription(UUID.randomUUID().toString());
        shopArticleDTO.setCommandExecuted(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final ShopArticleDTO created = this.shopArticleService.store(shopArticleDTO, new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[0]));
            assertEquals(shopArticleDTO.getName(), created.getName());
            assertNotNull(created.getCategory());
            assertEquals(shopArticleDTO.getCategory(), created.getCategory());
            assertEquals(shopArticleDTO.getDescription(), created.getDescription());
            assertEquals(shopArticleDTO.getHtmlDescription(), created.getHtmlDescription());
            assertEquals(shopArticleDTO.getPrice(), created.getPrice());

            this.shopArticleService.loadAsResource(created.getId().toString());
            this.shopArticleService.delete(created.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () -> this.shopArticleService.loadAsResource(created.getId().toString()));
        });
    }

    @Test
    void patchEntity() {
        final ShopCategoryDTO shopCategoryDTO = this.generateCategory();
        final ShopArticleDTO shopArticleDTO = new ShopArticleDTO();
        shopArticleDTO.setCategory(shopCategoryDTO);
        shopArticleDTO.setName(UUID.randomUUID().toString());
        shopArticleDTO.setDescription(UUID.randomUUID().toString());
        shopArticleDTO.setPrice(10.0);
        shopArticleDTO.setHtmlDescription(UUID.randomUUID().toString());
        shopArticleDTO.setCommandExecuted(UUID.randomUUID().toString());

        final String fileName = "fileNameTest" + UUID.randomUUID();
        final String fileExt = "txt";
        final String fileContent = "test";
        final MockMultipartFile file = new MockMultipartFile("file", fileName + "." + fileExt, "image/jpeg", fileContent.getBytes());

        assertDoesNotThrow(() -> {
            final ShopArticleDTO created = this.shopArticleService.store(shopArticleDTO, file);
            created.setName(UUID.randomUUID().toString());
            created.setPrice(100.0);

            final String fileContent2 = "test";
            final MockMultipartFile file2 = new MockMultipartFile("file2", fileName + "." + fileExt, "image/jpeg", fileContent2.getBytes());

            final ShopArticleDTO patched = this.shopArticleService.updateFull(created, file2);
            assertEquals(created.getName(), patched.getName());
            assertEquals(created.getPrice(), patched.getPrice());
            assertEquals(shopArticleDTO.getDescription(), patched.getDescription());

            final Resource resource = this.shopArticleService.loadAsResource(patched.getId().toString());
            assertNotNull(resource);
            assertEquals(fileContent2, new String(resource.getInputStream().readAllBytes()));
        });
    }

    private ShopCategoryDTO generateCategory() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());
        shopCategoryDTO.setDescription(UUID.randomUUID().toString());
        shopCategoryDTO.setMultiPurchaseAllowed(false);

        return this.shopCategoryService.create(shopCategoryDTO);
    }

}
