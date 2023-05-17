package fr.pacifista.api.client.web.shop.categories.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.web.shop.categories.dtos.ShopCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShopCategories", url = "${pacifista.api.app-domain-url}", path = "/web/shop/categories")
public interface ShopCategoriesClient extends CrudClient<ShopCategoryDTO> {
}
