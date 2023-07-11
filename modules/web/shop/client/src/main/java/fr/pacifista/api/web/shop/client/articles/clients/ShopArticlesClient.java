package fr.pacifista.api.web.shop.client.articles.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShopArticles", url = "${pacifista.api.web.shop.app-domain-url}", path = "/web/shop/articles", configuration = FeignTokenInterceptor.class)
public interface ShopArticlesClient extends CrudClient<ShopArticleDTO> {
}
