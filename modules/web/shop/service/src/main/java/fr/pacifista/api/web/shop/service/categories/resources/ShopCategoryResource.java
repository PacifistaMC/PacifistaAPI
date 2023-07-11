package fr.pacifista.api.web.shop.service.categories.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.shop.client.categories.clients.ShopCategoriesClient;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/shop/categories")
public class ShopCategoryResource extends ApiResource<ShopCategoryDTO, ShopCategoryService> implements ShopCategoriesClient {

    public ShopCategoryResource(ShopCategoryService service) {
        super(service);
    }

}
