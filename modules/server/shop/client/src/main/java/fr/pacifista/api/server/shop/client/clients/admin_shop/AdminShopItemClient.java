package fr.pacifista.api.server.shop.client.clients.admin_shop;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "AdminShopItemClient",
        url = "${pacifista.api.server.shop.app-domain-url}",
        path = AdminShopItemImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface AdminShopItemClient extends CrudClient<AdminShopItemDTO> {
}
