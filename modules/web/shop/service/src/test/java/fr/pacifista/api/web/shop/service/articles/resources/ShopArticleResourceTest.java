package fr.pacifista.api.web.shop.service.articles.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.ShopResourceModuleTest;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ShopArticleResourceTest extends ShopResourceModuleTest<ShopArticleDTO> {

    @MockBean
    ShopArticleService articleService;

    @BeforeEach
    void setupMock() {
        super.route = "/web/shop/articles";

        reset(articleService);
        when(articleService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(articleService.create(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        when(articleService.update(any(ShopArticleDTO.class))).thenReturn(new ShopArticleDTO());
        doNothing().when(articleService).delete(anyString());
    }

    @Override
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
