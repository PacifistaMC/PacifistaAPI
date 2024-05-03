package fr.pacifista.api.server.shop.client.clients.shop;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;

public class PlayerChestShopImplClient extends FeignImpl<PlayerChestShopDTO, PlayerChestShopClient> {

    public static final String PATH = "shop/chests";

    public PlayerChestShopImplClient() {
        super(PATH, PlayerChestShopClient.class);
    }

}
