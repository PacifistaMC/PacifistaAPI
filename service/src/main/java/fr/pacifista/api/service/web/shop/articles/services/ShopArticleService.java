package fr.pacifista.api.service.web.shop.articles.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.web.shop.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.service.web.shop.articles.entities.ShopArticle;
import fr.pacifista.api.service.web.shop.articles.mappers.ShopArticleMapper;
import fr.pacifista.api.service.web.shop.articles.repositories.ShopArticleRepository;
import fr.pacifista.api.service.web.shop.categories.entities.ShopCategory;
import fr.pacifista.api.service.web.shop.categories.services.ShopCategoryService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ShopArticleService extends ApiService<ShopArticleDTO, ShopArticle, ShopArticleMapper, ShopArticleRepository> {

    private final ShopCategoryService shopCategoryService;

    public ShopArticleService(ShopArticleRepository repository,
                              ShopArticleMapper mapper,
                              ShopCategoryService shopCategoryService) {
        super(repository, mapper);
        this.shopCategoryService = shopCategoryService;
    }

    @Override
    public void afterMapperCall(@NonNull ShopArticleDTO dto, @NonNull ShopArticle entity) {
        final ShopCategory category = this.shopCategoryService.findById(dto.getCategory().getId());
        entity.setCategory(category);

        this.shopCategoryService.getRepository().save(category);
    }

}
