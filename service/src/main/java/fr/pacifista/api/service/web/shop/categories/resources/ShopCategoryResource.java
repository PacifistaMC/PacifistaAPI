package fr.pacifista.api.service.web.shop.categories.resources;


import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.web.shop.categories.clients.ShopCategoriesClient;
import fr.pacifista.api.client.web.shop.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.service.web.shop.categories.services.ShopCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/shop/categories")
public class ShopCategoryResource extends ApiResource<ShopCategoryDTO, ShopCategoryService> implements ShopCategoriesClient {

    public ShopCategoryResource(ShopCategoryService service) {
        super(service);
    }

}
