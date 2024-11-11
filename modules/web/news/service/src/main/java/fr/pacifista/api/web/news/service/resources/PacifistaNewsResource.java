package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsClient;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.services.PacifistaNewsService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/web/news")
public class PacifistaNewsResource extends ApiResource<PacifistaNewsDTO, PacifistaNewsService> implements PacifistaNewsClient {

    public PacifistaNewsResource(PacifistaNewsService pacifistaNewsService) {
        super(pacifistaNewsService);
    }

    @Override
    public PageDTO<PacifistaNewsDTO> getAll(int page) {
    }

    @Override
    public PacifistaNewsDTO getNewsById(String newsId) {
    }

    @Override
    public Resource getImageNews(String imageId) {
    }

    @Override
    public PacifistaNewsDTO createNews(PacifistaNewsDTO newsDTO, MultipartFile image) {
    }

    @Override
    public PacifistaNewsDTO updateNews(PacifistaNewsDTO newsDTO) {
    }

    @Override
    public PacifistaNewsDTO updateNews(PacifistaNewsDTO newsDTO, MultipartFile image) {
    }

    @Override
    public void deleteNews(String newsId) {
    }

    @Override
    public PageDTO<PacifistaNewsLikeDTO> getLikesOnNews(String newsId, int page) {
    }

    @Override
    public PacifistaNewsLikeDTO likeNews(String newsId) {
    }

    @Override
    public void removeLikeOnNews(String newsId) {
    }
}
