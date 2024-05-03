package fr.pacifista.api.server.shop.service.services.admin_shop;

import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopPlayerLimit;
import fr.pacifista.api.server.shop.service.mappers.admin_shop.AdminShopPlayerLimitMapper;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopPlayerLimitRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminShopPlayerLimitService extends AdminShopDataWithCategoryService<AdminShopPlayerLimitDTO, AdminShopPlayerLimit, AdminShopPlayerLimitMapper, AdminShopPlayerLimitRepository> {

    public AdminShopPlayerLimitService(AdminShopPlayerLimitRepository repository,
                                       AdminShopPlayerLimitMapper mapper,
                                       AdminShopCategoryRepository adminShopCategoryRepository) {
        super(repository, mapper, adminShopCategoryRepository);
    }

}
