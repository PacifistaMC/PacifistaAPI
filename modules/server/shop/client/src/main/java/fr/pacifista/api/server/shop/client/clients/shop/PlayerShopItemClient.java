package fr.pacifista.api.server.shop.client.clients.shop;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerShopItemClient",
        url = "${pacifista.api.server.shop.app-domain-url}",
        path = PlayerShopItemImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface PlayerShopItemClient extends CrudClient<PlayerShopItemDTO> {
}
