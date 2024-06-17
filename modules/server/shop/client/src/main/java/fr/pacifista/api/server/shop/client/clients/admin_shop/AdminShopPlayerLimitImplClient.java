package fr.pacifista.api.server.shop.client.clients.admin_shop;

import feign.FeignException;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;

public class AdminShopPlayerLimitImplClient extends FeignImpl<AdminShopPlayerLimitDTO, AdminShopPlayerLimitClient> implements AdminShopPlayerLimitClient {

    public static final String PATH = "shop/admin/playerlimit";

    public AdminShopPlayerLimitImplClient() {
        super(PATH, AdminShopPlayerLimitClient.class);
    }

    @Override
    public void resetPlayerLimits() {
        try {
            super.getClient().resetPlayerLimits();
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }
}
