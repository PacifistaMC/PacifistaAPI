package fr.pacifista.api.server.shop.service.resources.admin_shop;

import com.funixproductions.core.crud.resources.ApiResource;
import com.funixproductions.core.exceptions.ApiException;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopPlayerLimitClient;
import fr.pacifista.api.server.shop.client.clients.admin_shop.AdminShopPlayerLimitImplClient;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import fr.pacifista.api.server.shop.service.services.admin_shop.AdminShopPlayerLimitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AdminShopPlayerLimitImplClient.PATH)
public class AdminShopPlayerLimitResource extends ApiResource<AdminShopPlayerLimitDTO, AdminShopPlayerLimitService> implements AdminShopPlayerLimitClient {
    public AdminShopPlayerLimitResource(AdminShopPlayerLimitService service) {
        super(service);
    }

    @Override
    public void resetPlayerLimits() {
        try {
            super.getService().getRepository().deleteAll();
        } catch (Exception e) {
            throw new ApiException("Impossible de réinitialiser les limites de joueurs de l'adminshop.", e);
        }
    }
}
