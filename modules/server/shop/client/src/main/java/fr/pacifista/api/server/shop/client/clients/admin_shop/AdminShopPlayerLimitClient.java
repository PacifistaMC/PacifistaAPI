package fr.pacifista.api.server.shop.client.clients.admin_shop;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

@FeignClient(
        name = "AdminShopPlayerLimitClient",
        url = "${pacifista.api.server.shop.app-domain-url}",
        path = AdminShopPlayerLimitImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface AdminShopPlayerLimitClient extends CrudClient<AdminShopPlayerLimitDTO> {

    @DeleteMapping("/reset-player-limits")
    void resetPlayerLimits();

}
