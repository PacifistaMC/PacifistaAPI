package fr.pacifista.api.server.shop.client.clients.admin_shop;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;

public class AdminShopCategoryImplClient extends FeignImpl<AdminShopCategoryDTO, AdminShopCategoryClient> {

    public static final String PATH = "shop/admin/categories";

    public AdminShopCategoryImplClient() {
        super(PATH, AdminShopCategoryClient.class);
    }

}
