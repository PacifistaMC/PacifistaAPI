package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopItemImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;
import fr.pacifista.api.server.shop.service.services.admin_shop.AdminShopItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AdminShopItemImplClient.PATH)
public class AdminShopItemResource extends ApiResource<AdminShopItemDTO, AdminShopItemService> {
    public AdminShopItemResource(AdminShopItemService service) {
        super(service);
    }
}
