package fr.pacifista.api.web.shop.service.categories.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import fr.pacifista.api.web.shop.service.categories.mappers.ShopCategoryMapper;
import fr.pacifista.api.web.shop.service.categories.repositories.ShopCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopCategoryService extends ApiService<ShopCategoryDTO, ShopCategory, ShopCategoryMapper, ShopCategoryRepository> {

    public ShopCategoryService(ShopCategoryRepository repository,
                               ShopCategoryMapper mapper) {
        super(repository, mapper);
    }

}
