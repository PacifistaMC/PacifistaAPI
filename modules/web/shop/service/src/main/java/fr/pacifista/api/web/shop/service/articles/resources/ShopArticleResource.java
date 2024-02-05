package fr.pacifista.api.web.shop.service.articles.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.shop.client.articles.clients.ShopArticlesClient;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/shop/articles")
public class ShopArticleResource extends ApiResource<ShopArticleDTO, ShopArticleService> implements ShopArticlesClient {

    public ShopArticleResource(ShopArticleService service) {
        super(service);
    }



}
