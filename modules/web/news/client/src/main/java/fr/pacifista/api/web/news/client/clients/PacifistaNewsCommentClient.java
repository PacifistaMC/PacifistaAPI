package fr.pacifista.api.web.news.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "PacifistaNewsComments",
        url = "${pacifista.web.news.api.app-domain-url}",
        path = "/web/news/comments",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaNewsCommentClient {

    @GetMapping
    PageDTO<PacifistaNewsCommentDTO> getCommentsOnNews(
            @RequestParam(value = "newsId") String newsId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

}
