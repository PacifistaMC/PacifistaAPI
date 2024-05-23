package fr.pacifista.api.server.shop.service.repositories.admin_shop;

import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopPlayerLimit;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminShopPlayerLimitRepository extends AdminShopCategoryRemovalRepository<AdminShopPlayerLimit> {
}
