package fr.pacifista.api.service.web.shop.articles.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.web.shop.articles.entities.ShopArticle;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopArticleRepository extends ApiRepository<ShopArticle> {
}
