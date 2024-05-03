package fr.pacifista.api.server.shop.client.clients.admin_shop;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "AdminShopCategoryClient",
        url = "${pacifista.api.server.shop.app-domain-url}",
        path = AdminShopCategoryImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface AdminShopCategoryClient extends CrudClient<AdminShopCategoryDTO> {
}
