package fr.pacifista.api.server.shop.service.repositories.admin_shop;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopDataWithCategory;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface AdminShopCategoryRemovalRepository<ENTITY extends AdminShopDataWithCategory> extends ApiRepository<ENTITY> {
    void deleteAllByCategoryIn(Iterable<AdminShopCategory> categories);
}
