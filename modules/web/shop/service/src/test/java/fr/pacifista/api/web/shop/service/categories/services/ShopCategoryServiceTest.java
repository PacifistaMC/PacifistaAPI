package fr.pacifista.api.web.shop.service.categories.services;

import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShopCategoryServiceTest {

    @Autowired
    ShopCategoryService shopCategoryService;

    @MockitoBean
    PacifistaPlusService pacifistaPlusService;

    @BeforeEach
    void mockPacifistaPlusService() {
        when(pacifistaPlusService.getPlan()).thenReturn(new PaypalPlanDTO());
    }

    @Test
    void createEntity() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());
        shopCategoryDTO.setDescription(UUID.randomUUID().toString());
        shopCategoryDTO.setMultiPurchaseAllowed(false);

        assertDoesNotThrow(() -> {
            final ShopCategoryDTO created = this.shopCategoryService.create(shopCategoryDTO);
            assertEquals(shopCategoryDTO.getName(), created.getName());
        });
    }

    @Test
    void patchEntity() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());
        shopCategoryDTO.setDescription(UUID.randomUUID().toString());
        shopCategoryDTO.setMultiPurchaseAllowed(false);

        assertDoesNotThrow(() -> {
            final ShopCategoryDTO created = this.shopCategoryService.create(shopCategoryDTO);
            created.setName(UUID.randomUUID().toString());

            final ShopCategoryDTO patched = this.shopCategoryService.update(created);
            assertEquals(created.getName(), patched.getName());
        });
    }

}
