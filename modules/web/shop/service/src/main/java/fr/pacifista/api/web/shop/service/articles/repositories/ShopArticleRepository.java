package fr.pacifista.api.web.shop.service.articles.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopArticleRepository extends ApiRepository<ShopArticle> {
}
