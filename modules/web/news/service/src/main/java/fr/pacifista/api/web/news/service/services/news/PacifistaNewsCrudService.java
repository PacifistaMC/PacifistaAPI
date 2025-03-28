package fr.pacifista.api.web.news.service.services.news;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.dtos.UserSession;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsLike;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentLikeCrudService;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PacifistaNewsCrudService extends ApiService<PacifistaNewsDTO, PacifistaNews, PacifistaNewsMapper, PacifistaNewsRepository> {

    private final CurrentSession actualSession;
    private final PacifistaWebUserLinkComponent userLinkComponent;

    private final PacifistaNewsImageCrudService imageCrudService;
    private final PacifistaNewsLikeCrudService likeCrudService;
    private final PacifistaNewsCommentCrudService commentCrudService;
    private final PacifistaNewsCommentLikeCrudService commentLikeCrudService;

    public PacifistaNewsCrudService(PacifistaNewsRepository repository,
                                    PacifistaNewsMapper mapper,
                                    CurrentSession actualSession,
                                    PacifistaWebUserLinkComponent userLinkComponent,
                                    PacifistaNewsLikeCrudService likeCrudService,
                                    PacifistaNewsImageCrudService imageCrudService,
                                    PacifistaNewsCommentCrudService commentCrudService,
                                    PacifistaNewsCommentLikeCrudService commentLikeCrudService) {
        super(repository, mapper);
        this.actualSession = actualSession;
        this.userLinkComponent = userLinkComponent;
        this.imageCrudService = imageCrudService;
        this.likeCrudService = likeCrudService;
        this.commentCrudService = commentCrudService;
        this.commentLikeCrudService = commentLikeCrudService;
    }

    @Override
    public void beforeSendingDTO(@NonNull Iterable<PacifistaNewsDTO> dto) {
        final UserDTO currentUser = this.actualSession.getCurrentUser();
        if (currentUser == null) return;

        try {
            final PacifistaWebUserLinkDTO link = this.userLinkComponent.getLink(currentUser);
            final Set<String> newsIdsList = new HashSet<>();

            for (final PacifistaNewsDTO newsDTO : dto) {
                newsIdsList.add(newsDTO.getId().toString());
            }

            final Iterable<PacifistaNews> allNews = super.getRepository().findAllByUuidIn(newsIdsList);
            final Iterable<PacifistaNewsLike> allLikes = this.likeCrudService.getRepository().findAllByNewsInAndMinecraftUsernameIgnoreCaseAndFunixProdUserId(allNews, link.getMinecraftUsername(), link.getFunixProdUserId());

            for (final PacifistaNewsDTO newsDTO : dto) {
                newsDTO.setLiked(
                        this.checkIfLikeInNewsListFound(newsDTO, allLikes)
                );
            }
        } catch (ApiBadRequestException ignored) {
        }
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNews> entity) {
        final UserSession session = this.actualSession.getUserSession();
        if (session == null) {
            throw new ApiBadRequestException("Vous n'êtes pas connecté.");
        }
        final PacifistaWebUserLinkDTO minecraftAccount = this.userLinkComponent.getLink(session.getUserDTO());

        for (final PacifistaNews e : entity) {
            if (e.getId() == null) {
                this.checkIfNewsAlreadyExistsWithThatName(e.getName());

                e.setLikes(0);
                e.setComments(0);
                e.setViews(0);
                e.setOriginalWriter(minecraftAccount.getMinecraftUsername());
            } else {
                e.setUpdateWriter(minecraftAccount.getMinecraftUsername());
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaNews> entities) {
        for (final PacifistaNews news : entities) {
            this.imageCrudService.deleteAllByNews(news);
            this.likeCrudService.deleteAllByNews(news);
            this.commentCrudService.deleteAllByNews(news);
            this.commentLikeCrudService.deleteAllByNews(news);
        }
    }

    public void setNewsLikeAmount(final UUID newsId, final int amount) {
        final Optional<PacifistaNews> searchedNews = super.getRepository().findByUuid(newsId.toString());

        if (searchedNews.isPresent()) {
            final PacifistaNews news = searchedNews.get();
            news.setLikes(amount);

            if (news.getLikes() < 0) {
                news.setLikes(0);
            }

            super.getRepository().save(news);
        }
    }

    public void setNewsViewsAmount(final UUID newsId, final int amount) {
        final Optional<PacifistaNews> searchedNews = super.getRepository().findByUuid(newsId.toString());

        if (searchedNews.isPresent()) {
            final PacifistaNews news = searchedNews.get();
            news.setViews(amount);

            if (news.getViews() < 0) {
                news.setViews(0);
            }

            super.getRepository().save(news);
        }
    }

    public void setNewsCommentsAmount(final UUID newsId, final int amount) {
        final Optional<PacifistaNews> searchedNews = super.getRepository().findByUuid(newsId.toString());

        if (searchedNews.isPresent()) {
            final PacifistaNews news = searchedNews.get();
            news.setComments(amount);

            if (news.getComments() < 0) {
                news.setComments(0);
            }

            super.getRepository().save(news);
        }
    }

    public boolean isCurrentUserStaff() {
        final UserSession currentSession = this.actualSession.getUserSession();

        if (currentSession == null || currentSession.getUserDTO() == null || currentSession.getUserDTO().getRole() == null) {
            return false;
        } else {
            final UserRole role = currentSession.getUserDTO().getRole();

            return role == UserRole.ADMIN ||
                    role == UserRole.PACIFISTA_ADMIN ||
                    role == UserRole.PACIFISTA_MODERATOR;
        }
    }

    private void checkIfNewsAlreadyExistsWithThatName(final String name) {
        if (super.getRepository().findByNameIgnoreCase(name).isPresent()) {
            throw new ApiBadRequestException(String.format("Un article avec le nom %s existe déjà.", name));
        }
    }

    private boolean checkIfLikeInNewsListFound(final PacifistaNewsDTO newsDTO, final Iterable<PacifistaNewsLike> likes) {
        for (final PacifistaNewsLike like : likes) {
            if (like.getNews().getUuid().equals(newsDTO.getId())) {
                return true;
            }
        }

        return false;
    }
}
