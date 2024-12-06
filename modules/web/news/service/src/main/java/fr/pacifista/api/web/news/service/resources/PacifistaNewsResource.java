package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import com.funixproductions.core.files.services.ApiStorageService;
import com.funixproductions.core.tools.network.IPUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsClient;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsImageDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsCrudService;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsImageCrudService;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsLikeCrudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/web/news")
@RequiredArgsConstructor
public class PacifistaNewsResource implements PacifistaNewsClient {

    private final IPUtils ipUtils;
    private final CurrentSession actualSession;
    private final HttpServletRequest servletRequest;

    private final Cache<String, String> viewIpCount = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    private final PacifistaNewsCrudService newsService;
    private final PacifistaNewsImageCrudService imageService;
    private final PacifistaNewsLikeCrudService likeService;

    @Override
    public PageDTO<PacifistaNewsDTO> getAll(int page) {
        final String search = this.newsService.isCurrentUserStaff() ? "" : String.format(
                "draft:%s:%s",
                SearchOperation.IS_FALSE.getOperation(),
                "false"
        );

        return this.newsService.getAll(
                Integer.toString(page),
                "20",
                search,
                "createdAt:desc"
        );
    }

    @Override
    public PacifistaNewsDTO getNewsById(String newsId) {
        final PacifistaNewsDTO pacifistaNewsDTO = this.newsService.findById(newsId);

        if (Boolean.TRUE.equals(pacifistaNewsDTO.getDraft()) && !this.newsService.isCurrentUserStaff()) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        }

        final String ip = this.ipUtils.getClientIp(this.servletRequest);

        if (!newsId.equals(viewIpCount.getIfPresent(ip))) {
            pacifistaNewsDTO.setViews(pacifistaNewsDTO.getViews() + 1);

            this.newsService.setNewsViewsAmount(pacifistaNewsDTO.getId(), pacifistaNewsDTO.getViews());
            this.viewIpCount.put(ip, newsId);
        }

        return pacifistaNewsDTO;
    }

    @Override
    @Transactional
    public PacifistaNewsDTO getNewsByName(String newsName) {
        PacifistaNews news = this.newsService.getRepository().findByNameIgnoreCase(newsName).orElseThrow(() -> new ApiNotFoundException("News introuvable"));
        if (Boolean.TRUE.equals(news.getDraft()) && !this.newsService.isCurrentUserStaff()) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        }

        final String ip = this.ipUtils.getClientIp(this.servletRequest);

        if (!news.getUuid().toString().equals(viewIpCount.getIfPresent(ip))) {
            news.setViews(news.getViews() + 1);

            this.newsService.setNewsViewsAmount(news.getUuid(), news.getViews());
            this.viewIpCount.put(ip, news.getUuid().toString());
        }

        return this.newsService.getMapper().toDto(news);
    }

    @Override
    public Resource getImageNews(String imageId) {
        return this.imageService.loadAsResource(imageId);
    }

    @Override
    @Transactional
    public PacifistaNewsDTO createNews(PacifistaNewsDTO newsDTO, MultipartFile image) {
        PacifistaNewsDTO createdNews = this.newsService.create(newsDTO);

        try {
            final PacifistaNewsImageDTO imageDTO = this.imageService.store(new PacifistaNewsImageDTO(createdNews.getId(), false), image);
            final PacifistaNewsImageDTO imageLowResDTO = this.imageService.store(
                    new PacifistaNewsImageDTO(createdNews.getId(), true),
                    this.createLowerResolutionFile(image)
            );

            createdNews.setArticleImageId(imageDTO.getId());
            createdNews.setArticleImageIdLowRes(imageLowResDTO.getId());
            createdNews = this.newsService.updatePut(createdNews);

            return this.removeUpdateDataWhenCreateRequest(createdNews);
        } catch (Exception e) {
            this.newsService.delete(createdNews.getId().toString());
            throw e;
        }
    }

    @Override
    public PacifistaNewsDTO updateNews(PacifistaNewsDTO newsDTO) {
        return this.newsService.updatePut(newsDTO);
    }

    @Override
    @Transactional
    public PacifistaNewsDTO updateNews(PacifistaNewsDTO newsDTO, MultipartFile image) {
        final PacifistaNewsDTO oldNews = this.newsService.findById(newsDTO.getId().toString());
        final PacifistaNewsDTO updatedNews = this.newsService.updatePut(newsDTO);

        try {
            final PacifistaNewsImageDTO imageDTO = this.imageService.store(new PacifistaNewsImageDTO(updatedNews.getId(), false), image);
            final PacifistaNewsImageDTO imageLowResDTO = this.imageService.store(
                    new PacifistaNewsImageDTO(updatedNews.getId(), true),
                    this.createLowerResolutionFile(image)
            );

            updatedNews.setArticleImageId(imageDTO.getId());
            updatedNews.setArticleImageIdLowRes(imageLowResDTO.getId());

            try {
                final PacifistaNewsDTO newOne = this.newsService.updatePut(updatedNews);
                if (oldNews.getArticleImageId() != null) {
                    this.imageService.delete(oldNews.getArticleImageId().toString());
                }
                if (oldNews.getArticleImageIdLowRes() != null) {
                    this.imageService.delete(oldNews.getArticleImageIdLowRes().toString());
                }
                return newOne;
            } catch (Exception e) {
                this.imageService.delete(imageDTO.getId().toString());
                this.imageService.delete(imageLowResDTO.getId().toString());
                throw e;
            }
        } catch (Exception e) {
            this.newsService.updatePut(oldNews);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteNews(String newsId) {
        this.newsService.delete(newsId);
    }

    @Override
    public PageDTO<PacifistaNewsLikeDTO> getLikesOnNews(String newsId, int page) {
        final PacifistaNewsDTO news = this.newsService.findById(newsId);

        if (Boolean.TRUE.equals(news.getDraft()) && !this.newsService.isCurrentUserStaff()) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        }

        return this.likeService.getAll(
                Integer.toString(page),
                "20",
                String.format(
                        "news.uuid:%s:%s",
                        SearchOperation.EQUALS.getOperation(),
                        newsId
                ),
                "createdAt:desc"
        );
    }

    @Override
    @Transactional
    public PacifistaNewsLikeDTO likeNews(String newsId) {
        final UserDTO currentUser = this.actualSession.getCurrentUser();
        if (currentUser == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour aimer une news.");
        }
        final PacifistaNewsDTO news = this.newsService.findById(newsId);
        if (!this.newsService.isCurrentUserStaff() && Boolean.TRUE.equals(news.getDraft())) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        }

        final PacifistaNewsLikeDTO likedNews = this.getLikeForUserAndNews(currentUser, newsId);

        if (likedNews != null) {
            throw new ApiBadRequestException("Vous avez déjà aimé cette news.");
        } else {
            PacifistaNewsLikeDTO like = new PacifistaNewsLikeDTO();
            like.setNews(news);
            like = this.likeService.create(like);

            this.newsService.setNewsLikeAmount(news.getId(), news.getLikes() + 1);
            return like;
        }
    }

    @Override
    @Transactional
    public void removeLikeOnNews(String newsId) {
        final UserDTO currentUser = this.actualSession.getCurrentUser();
        if (currentUser == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour aimer une news.");
        }
        final PacifistaNewsDTO news = this.newsService.findById(newsId);
        if (!this.newsService.isCurrentUserStaff() && Boolean.TRUE.equals(news.getDraft())) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        }

        final PacifistaNewsLikeDTO likedNews = this.getLikeForUserAndNews(currentUser, newsId);

        if (likedNews != null) {
            this.likeService.delete(likedNews.getId().toString());
            this.newsService.setNewsLikeAmount(news.getId(), news.getLikes() - 1);
        } else {
            throw new ApiBadRequestException("Vous n'avez pas encore aimé cette news.");
        }
    }

    @Nullable
    private PacifistaNewsLikeDTO getLikeForUserAndNews(final UserDTO user, final String newsId) {
        try {
            return this.likeService.getAll(
                    "0",
                    "1",
                    String.format(
                            "news.uuid:%s:%s,funixProdUserId:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            newsId,
                            SearchOperation.EQUALS.getOperation(),
                            user.getId()
                    ),
                    "createdAt:desc"
            ).getContent().getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private MultipartFile createLowerResolutionFile(MultipartFile original) {
        final byte[] bytes = ApiStorageService.createThumbnailFromImage(original, 350);

        return new MultipartFile() {
            @NotNull
            @Override
            public String getName() {
                return "low-ver" + original.getName();
            }

            @Override
            public String getOriginalFilename() {
                return "low-ver" + original.getOriginalFilename();
            }

            @Override
            public String getContentType() {
                return original.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @NotNull
            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @NotNull
            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(@NotNull File dest) throws IOException {
                try (FileOutputStream fos = new FileOutputStream(dest)) {
                    fos.write(bytes);
                }
            }
        };
    }

    private PacifistaNewsDTO removeUpdateDataWhenCreateRequest(final PacifistaNewsDTO createdDto) {
        final PacifistaNews news = this.newsService.getRepository().findByUuid(createdDto.getId().toString()).orElseThrow(() -> new ApiNotFoundException("News introuvable"));
        news.setUpdateWriter(null);
        news.setUpdatedAt(null);

        return this.newsService.getMapper().toDto(
                this.newsService.getRepository().save(news)
        );
    }

}
