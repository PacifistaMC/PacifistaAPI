package fr.pacifista.api.server.shop.service.repositories.admin_shop;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminShopCategoryRepository extends ApiRepository<AdminShopCategory> {
    boolean existsByNameIgnoreCase(String name);
}
