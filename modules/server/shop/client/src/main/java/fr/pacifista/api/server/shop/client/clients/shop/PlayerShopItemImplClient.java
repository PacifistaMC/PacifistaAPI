package fr.pacifista.api.server.shop.client.clients.shop;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;

public class PlayerShopItemImplClient extends FeignImpl<PlayerShopItemDTO, PlayerShopItemClient> {

    public static final String PATH = "shop/items";

    public PlayerShopItemImplClient() {
        super(PATH, PlayerShopItemClient.class);
    }

}
