package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopPlayerLimitImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import fr.pacifista.api.server.shop.service.services.admin_shop.AdminShopPlayerLimitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AdminShopPlayerLimitImplClient.PATH)
public class AdminShopPlayerLimitResource extends ApiResource<AdminShopPlayerLimitDTO, AdminShopPlayerLimitService> {
    public AdminShopPlayerLimitResource(AdminShopPlayerLimitService service) {
        super(service);
    }
}
