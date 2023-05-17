package fr.pacifista.api.client.web.shop.articles.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.web.shop.articles.dtos.ShopArticleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShopArticles", url = "${pacifista.api.app-domain-url}", path = "/web/shop/articles")
public interface ShopArticlesClient extends CrudClient<ShopArticleDTO> {
}
