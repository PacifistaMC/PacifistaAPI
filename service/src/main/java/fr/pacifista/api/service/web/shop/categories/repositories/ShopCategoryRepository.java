package fr.pacifista.api.service.web.shop.categories.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.web.shop.categories.entities.ShopCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryRepository extends ApiRepository<ShopCategory> {
}
