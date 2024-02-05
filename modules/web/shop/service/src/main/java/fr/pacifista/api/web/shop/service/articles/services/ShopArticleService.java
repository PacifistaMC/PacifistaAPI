package fr.pacifista.api.web.shop.service.articles.services;

import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.files.services.ApiStorageService;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.articles.mappers.ShopArticleMapper;
import fr.pacifista.api.web.shop.service.articles.repositories.ShopArticleRepository;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import fr.pacifista.api.web.shop.service.categories.services.ShopCategoryService;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopArticlePurchaseRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopArticleService extends ApiStorageService<ShopArticleDTO, ShopArticle, ShopArticleMapper, ShopArticleRepository> {

    private final ShopCategoryService shopCategoryService;
    private final ShopArticlePurchaseRepository shopArticlePurchaseRepository;

    public ShopArticleService(ShopArticleRepository repository,
                              ShopArticleMapper mapper,
                              ShopCategoryService shopCategoryService,
                              ShopArticlePurchaseRepository shopArticlePurchaseRepository) {
        super("pacifista_shop_web_articles", repository, mapper);
        this.shopCategoryService = shopCategoryService;
        this.shopArticlePurchaseRepository = shopArticlePurchaseRepository;
    }

    public ShopArticleDTO createArticleWithImage(final ShopArticleDTO request, final MultipartFile image) {
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<ShopArticle> articles) {
        final Iterable<String> uids = getUuidsFromCategories(articles);
        final Iterable<ShopCategory> categories = shopCategoryService.getRepository().findAllByUuidIn(uids);

        for (ShopArticle article : articles) {
            if (article.getCategory() != null && article.getCategory().getUuid() != null) {
                final ShopCategory category = shopCategoryService.getEntityFromUidInList(categories, article.getCategory().getUuid());

                if (category != null) {
                    article.setCategory(category);
                } else {
                    throw new ApiNotFoundException(String.format("Catégorie avec id %s non trouvé.", article.getCategory().getUuid()));
                }
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<ShopArticle> entity) {
        for (ShopArticle article : entity) {
            this.shopArticlePurchaseRepository.deleteAllByArticle(article);
        }
    }

    private List<String> getUuidsFromCategories(@NonNull Iterable<ShopArticle> articles) {
        final List<String> uids = new ArrayList<>();

        for (ShopArticle category : articles) {
            if (category.getCategory() != null) {
                uids.add(category.getCategory().getUuid().toString());
            }
        }
        return uids;
    }

}
