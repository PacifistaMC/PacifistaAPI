package fr.pacifista.api.web.shop.service.categories.services;

import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShopCategoryServiceTest {

    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    void createEntity() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final ShopCategoryDTO created = this.shopCategoryService.create(shopCategoryDTO);
            assertEquals(shopCategoryDTO.getName(), created.getName());
        });
    }

    @Test
    void patchEntity() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final ShopCategoryDTO created = this.shopCategoryService.create(shopCategoryDTO);
            created.setName(UUID.randomUUID().toString());

            final ShopCategoryDTO patched = this.shopCategoryService.update(created);
            assertEquals(created.getName(), patched.getName());
        });
    }

}
