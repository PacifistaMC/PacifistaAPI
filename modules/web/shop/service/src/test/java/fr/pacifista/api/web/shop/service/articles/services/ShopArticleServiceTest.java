package fr.pacifista.api.web.shop.service.articles.services;

import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopArticleServiceTest {

    @Autowired
    ShopArticleService shopArticleService;

    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    void createEntity() {
        final ShopCategoryDTO shopCategoryDTO = this.generateCategory();
        final ShopArticleDTO shopArticleDTO = new ShopArticleDTO();
        shopArticleDTO.setCategory(shopCategoryDTO);
        shopArticleDTO.setName(UUID.randomUUID().toString());
        shopArticleDTO.setDescription(UUID.randomUUID().toString());
        shopArticleDTO.setPrice(10.0);
        shopArticleDTO.setHtmlDescription(UUID.randomUUID().toString());
        shopArticleDTO.setLogoUrl(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final ShopArticleDTO created = this.shopArticleService.create(shopArticleDTO);
            assertEquals(shopArticleDTO.getName(), created.getName());
            assertNotNull(created.getCategory());
            assertEquals(shopArticleDTO.getCategory(), created.getCategory());
            assertEquals(shopArticleDTO.getDescription(), created.getDescription());
            assertEquals(shopArticleDTO.getHtmlDescription(), created.getHtmlDescription());
            assertEquals(shopArticleDTO.getLogoUrl(), created.getLogoUrl());
            assertEquals(shopArticleDTO.getPrice(), created.getPrice());
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
        shopArticleDTO.setLogoUrl(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final ShopArticleDTO created = this.shopArticleService.create(shopArticleDTO);
            created.setName(UUID.randomUUID().toString());
            created.setPrice(100.0);

            final ShopArticleDTO patched = this.shopArticleService.update(created);
            assertEquals(created.getName(), patched.getName());
            assertEquals(created.getPrice(), patched.getPrice());
            assertEquals(shopArticleDTO.getDescription(), patched.getDescription());
        });
    }

    private ShopCategoryDTO generateCategory() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());

        return this.shopCategoryService.create(shopCategoryDTO);
    }

}