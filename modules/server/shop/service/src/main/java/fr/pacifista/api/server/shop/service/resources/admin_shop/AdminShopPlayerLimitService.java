package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopPlayerLimitImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AdminShopPlayerLimitImplClient.PATH)
public class AdminShopPlayerLimitService extends ApiResource<AdminShopPlayerLimitDTO, AdminShopPlayerLimitService> {
    public AdminShopPlayerLimitService(AdminShopPlayerLimitService service) {
        super(service);
    }
}
