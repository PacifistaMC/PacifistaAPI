package fr.pacifista.api.web.shop.service.categories.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryRepository extends ApiRepository<ShopCategory> {
}
