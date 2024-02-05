package fr.pacifista.api.web.shop.client.articles.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "ShopArticles",
        url = "${pacifista.api.web.shop.app-domain-url}",
        path = "/web/shop/articles",
        configuration = FeignTokenInterceptor.class)
public interface ShopArticlesClient extends CrudClient<ShopArticleDTO> {

    @PostMapping("/create")
    ShopArticleDTO create(@RequestBody @Valid ShopArticleDTO dto, @RequestParam MultipartFile shopLogo);

    @GetMapping("/logo/{articleId}")
    Resource getLogo(@PathVariable String articleId);

}
