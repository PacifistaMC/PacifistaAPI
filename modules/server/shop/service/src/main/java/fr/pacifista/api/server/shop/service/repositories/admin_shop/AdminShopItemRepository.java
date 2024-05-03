package fr.pacifista.api.server.shop.service.repositories.admin_shop;

import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopItem;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminShopItemRepository extends AdminShopCategoryRemovalRepository<AdminShopItem> {
}
