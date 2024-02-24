package fr.pacifista.api.web.shop.client.articles.clients;

import com.funixproductions.core.files.clients.StorageCrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShopArticles",
        url = "${pacifista.api.web.shop.app-domain-url}",
        path = "/web/shop/articles",
        configuration = FeignTokenInterceptor.class)
public interface ShopArticlesClient extends StorageCrudClient<ShopArticleDTO> {

}
