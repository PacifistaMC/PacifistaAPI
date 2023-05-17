package fr.pacifista.api.service.web.shop.articles.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.web.shop.articles.entities.ShopArticle;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopArticleRepository extends ApiRepository<ShopArticle> {
}
