package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.api.user.client.dtos.UserSession;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.resources.ApiResource;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.tools.network.IPUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsClient;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsCrudService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/web/news")
public class PacifistaNewsResource extends ApiResource<PacifistaNewsDTO, PacifistaNewsCrudService> implements PacifistaNewsClient {

    private final IPUtils ipUtils;
    private final CurrentSession actualSession;
    private final HttpServletRequest servletRequest;

    private final Cache<String, Boolean> viewIpCount = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    public PacifistaNewsResource(PacifistaNewsCrudService pacifistaNewsService,
                                 IPUtils ipUtils,
                                 CurrentSession actualSession,
                                 HttpServletRequest servletRequest) {
        super(pacifistaNewsService);
        this.ipUtils = ipUtils;
        this.servletRequest = servletRequest;
        this.actualSession = actualSession;
    }

    @Override
    public PageDTO<PacifistaNewsDTO> getAll(int page) {
    }

    @Override
    public PacifistaNewsDTO getNewsById(String newsId) {
        final PacifistaNewsDTO pacifistaNewsDTO = super.getService().findById(newsId);

        if (Boolean.TRUE.equals(pacifistaNewsDTO.getDraft()) && !this.canActualUserSeeDrafts()) {
            throw new ApiNotFoundException(String.format("L'entité id %s n'existe pas.", newsId));
        }

        final String ip = this.ipUtils.getClientIp(this.servletRequest);

        if (!Boolean.TRUE.equals(viewIpCount.getIfPresent(ip))) {
            PacifistaNews news = super.getService().getRepository().findByUuid(newsId).orElseThrow(() -> new ApiNotFoundException("News introuvable"));
            news.setViews(news.getViews() + 1);
            news = super.getService().getRepository().save(news);

            pacifistaNewsDTO.setViews(news.getViews());
            this.viewIpCount.put(ip, true);
        }

        return pacifistaNewsDTO;
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

    private boolean canActualUserSeeDrafts() {
        final UserSession currentSession = this.actualSession.getUserSession();

        if (currentSession == null || currentSession.getUserDTO() == null) {
            return false;
        } else {
            return currentSession.getUserDTO().getRole() == UserRole.ADMIN ||
                    currentSession.getUserDTO().getRole() == UserRole.PACIFISTA_ADMIN ||
                    currentSession.getUserDTO().getRole() == UserRole.PACIFISTA_MODERATOR;
        }
    }
}
