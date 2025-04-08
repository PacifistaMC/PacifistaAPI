package fr.pacifista.api.web.shop.service.categories.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import fr.pacifista.api.web.shop.service.categories.mappers.ShopCategoryMapper;
import fr.pacifista.api.web.shop.service.categories.repositories.ShopCategoryRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryService extends ApiService<ShopCategoryDTO, ShopCategory, ShopCategoryMapper, ShopCategoryRepository> {

    private final ShopArticleService shopArticleService;

    public ShopCategoryService(ShopCategoryRepository repository,
                               ShopCategoryMapper mapper,
                               ShopArticleService shopArticleService) {
        super(repository, mapper);
        this.shopArticleService = shopArticleService;
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<ShopCategory> entity) {
        final List<String> categoryIds = new ArrayList<>();

        for (final ShopCategory category : entity) {
            categoryIds.add(category.getId().toString());
        }

        this.shopArticleService.delete(categoryIds.toArray(new String[0]));
    }
}
