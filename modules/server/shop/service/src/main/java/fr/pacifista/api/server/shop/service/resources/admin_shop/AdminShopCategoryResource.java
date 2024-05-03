package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopCategoryImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import fr.pacifista.api.server.shop.service.services.admin_shop.AdminShopCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AdminShopCategoryImplClient.PATH)
public class AdminShopCategoryResource extends ApiResource<AdminShopCategoryDTO, AdminShopCategoryService> {
    public AdminShopCategoryResource(AdminShopCategoryService service) {
        super(service);
    }
}
