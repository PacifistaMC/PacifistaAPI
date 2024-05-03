package fr.pacifista.api.server.shop.service.services.admin_shop;

import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopItem;
import fr.pacifista.api.server.shop.service.mappers.admin_shop.AdminShopItemMapper;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopItemRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminShopItemService extends AdminShopDataWithCategoryService<AdminShopItemDTO, AdminShopItem, AdminShopItemMapper, AdminShopItemRepository> {

    public AdminShopItemService(AdminShopItemRepository repository,
                                AdminShopItemMapper mapper,
                                AdminShopCategoryRepository adminShopCategoryRepository) {
        super(repository, mapper, adminShopCategoryRepository);
    }

}
