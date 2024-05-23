package fr.pacifista.api.server.shop.client.clients.shop;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerChestShopClient",
        url = "${pacifista.api.server.shop.app-domain-url}",
        path = PlayerChestShopImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface PlayerChestShopClient extends CrudClient<PlayerChestShopDTO> {
}
