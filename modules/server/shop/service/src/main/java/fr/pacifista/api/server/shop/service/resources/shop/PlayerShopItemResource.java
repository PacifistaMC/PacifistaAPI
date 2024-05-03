package fr.pacifista.api.server.shop.service.resources.shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.shop.PlayerShopItemImplClient;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;
import fr.pacifista.api.server.shop.service.services.shop.PlayerShopItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PlayerShopItemImplClient.PATH)
public class PlayerShopItemResource extends ApiResource<PlayerShopItemDTO, PlayerShopItemService> {

    public PlayerShopItemResource(PlayerShopItemService service) {
        super(service);
    }

}
