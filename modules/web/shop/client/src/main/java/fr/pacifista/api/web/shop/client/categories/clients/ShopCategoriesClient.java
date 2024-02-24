package fr.pacifista.api.web.shop.client.categories.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShopCategories", url = "${pacifista.api.web.shop.app-domain-url}", path = "/web/shop/categories", configuration = FeignTokenInterceptor.class)
public interface ShopCategoriesClient extends CrudClient<ShopCategoryDTO> {
}
