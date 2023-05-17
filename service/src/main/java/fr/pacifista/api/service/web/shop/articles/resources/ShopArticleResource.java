package fr.pacifista.api.service.web.shop.articles.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.web.shop.articles.clients.ShopArticlesClient;
import fr.pacifista.api.client.web.shop.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.service.web.shop.articles.services.ShopArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/shop/articles")
public class ShopArticleResource extends ApiResource<ShopArticleDTO, ShopArticleService> implements ShopArticlesClient {

    public ShopArticleResource(ShopArticleService service) {
        super(service);
    }

}
