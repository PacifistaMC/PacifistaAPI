package fr.pacifista.api.server.shop.client.clients.admin_shop;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;

public class AdminShopItemImplClient extends FeignImpl<AdminShopItemDTO, AdminShopItemClient> {

    public static final String PATH = "shop/admin/items";

    public AdminShopItemImplClient() {
        super(PATH, AdminShopItemClient.class);
    }
}
