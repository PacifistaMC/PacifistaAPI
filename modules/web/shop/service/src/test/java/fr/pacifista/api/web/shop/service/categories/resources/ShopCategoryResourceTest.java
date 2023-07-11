package fr.pacifista.api.web.shop.service.categories.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.ShopResourceModuleTest;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ShopCategoryResourceTest extends ShopResourceModuleTest<ShopCategoryDTO> {

    @MockBean
    ShopCategoryService categoryService;

    @BeforeEach
    void setupMocks() {
        super.route = "/web/shop/categories";

        reset(categoryService);
        when(categoryService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(categoryService.create(any(ShopCategoryDTO.class))).thenReturn(new ShopCategoryDTO());
        when(categoryService.update(any(ShopCategoryDTO.class))).thenReturn(new ShopCategoryDTO());
        doNothing().when(categoryService).delete(anyString());
    }

    @Override
    public ShopCategoryDTO generateDTO() {
        final ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
        shopCategoryDTO.setName(UUID.randomUUID().toString());

        return shopCategoryDTO;
    }
}
