package fr.pacifista.api.server.shop.service.resources.shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.shop.PlayerChestShopImplClient;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;
import fr.pacifista.api.server.shop.service.services.shop.PlayerChestShopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + PlayerChestShopImplClient.PATH)
public class PlayerChestShopResource extends ApiResource<PlayerChestShopDTO, PlayerChestShopService> {

    public PlayerChestShopResource(PlayerChestShopService service) {
        super(service);
    }

}
