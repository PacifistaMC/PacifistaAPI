package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsCommentClient;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.services.ban.PacifistaNewsBanCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentLikeCrudService;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsCrudService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/web/news/comments")
@RequiredArgsConstructor
public class PacifistaNewsCommentResource implements PacifistaNewsCommentClient {

    private final PacifistaNewsCommentCrudService service;
    private final PacifistaNewsCommentLikeCrudService likeService;
    private final PacifistaNewsCrudService newsService;
    private final PacifistaNewsBanCrudService banService;
    private final CurrentSession currentSession;

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsOnNews(String newsId, int page) {
        final PacifistaNewsDTO news = this.newsService.findById(newsId);
        final boolean canUserSeeDrafts = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(news.getDraft()) && !canUserSeeDrafts) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        } else {
            return this.service.getAll(
                    Integer.toString(page),
                    "20",
                    String.format(
                            "news.uuid:%s:%s,parent:%s:null",
                            SearchOperation.EQUALS.getOperation(),
                            newsId,
                            SearchOperation.IS_NULL.getOperation()
                    ),
                    "likes:desc"
            );
        }
    }

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsRepliesOnNews(String commentId, int page) {
        final PacifistaNewsCommentDTO comment = this.service.findById(commentId);
        final boolean canUserSeeDrafts = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(comment.getNews().getDraft()) && !canUserSeeDrafts) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        } else {
            return this.service.getAll(
                    Integer.toString(page),
                    "10",
                    String.format(
                            "parent.uuid:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            commentId
                    ),
                    "createdAt:desc"
            );
        }
    }

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsByUser(String minecraftUsername, int page) {
        final boolean canUserSeeDrafts = this.newsService.isCurrentUserStaff();

        return this.service.getAll(
                Integer.toString(page),
                "20",
                String.format(
                        "minecraftUsername:%s:%s%s",
                        SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                        minecraftUsername,
                        canUserSeeDrafts ? "" : String.format(",news.draft:%s:false", SearchOperation.IS_FALSE.getOperation())
                ),
                "createdAt:desc"
        );
    }

    @Override
    @Transactional
    public PacifistaNewsCommentDTO createComment(final PacifistaNewsCommentDTO commentDTO) {
        if (Boolean.TRUE.equals(this.banService.isCurrentUserBanned())) {
            throw new ApiForbiddenException("Vous avez été banni de l'espace commentaire et ne pouvez pas commenter.");
        }
        if (commentDTO.getNews().getId() == null) {
            throw new ApiBadRequestException("Vous devez spécifier la news à laquelle vous répondez.");
        }
        if (commentDTO.getParent() != null && commentDTO.getParent().getId() == null) {
            throw new ApiBadRequestException("Vous devez spécifier le commentaire parent.");
        }

        final PacifistaNewsDTO news = this.newsService.findById(commentDTO.getNews().getId().toString());
        final boolean canUserComment = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(news.getDraft()) && !canUserComment) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de commenter les brouillons.");
        } else {
            commentDTO.setLikes(0);
            commentDTO.setReplies(0);

            final PacifistaNewsCommentDTO res = this.service.create(commentDTO);
            this.newsService.setNewsCommentsAmount(news.getId(), news.getComments() + 1);

            if (res.getParent() != null) {
                final PacifistaNewsCommentDTO parent = res.getParent();

                parent.setReplies(parent.getReplies() + 1);
                this.service.setCommentReplies(parent.getId(), parent.getReplies());
            }
            return res;
        }
    }

    @Override
    @Transactional
    public PacifistaNewsCommentDTO updateComment(String commentId, String comment) {
        final PacifistaNewsCommentDTO commentDTO = this.checkFilterEditOrCreate(commentId, true);

        commentDTO.setContent(comment);

        final PacifistaNewsCommentDTO res = this.service.updatePut(commentDTO);
        this.service.alertDiscordWhenCommentUpdated(res);

        return res;
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        this.checkFilterEditOrCreate(commentId, false);
        this.service.delete(commentId);
    }

    @Override
    public PageDTO<PacifistaNewsCommentLikeDTO> getLikesOnComment(String commentId, int page) {
        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);
        final boolean canUserSeeDrafts = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !canUserSeeDrafts) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de voir les brouillons.");
        } else {
            return this.likeService.getAll(
                    Integer.toString(page),
                    "20",
                    String.format(
                            "comment.uuid:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            commentId
                    ),
                    "createdAt:desc"
            );
        }
    }

    @Override
    @Transactional
    public PacifistaNewsCommentLikeDTO likeComment(String commentId) {
        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);
        final boolean canUserEdit = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !canUserEdit) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de modifier les brouillons.");
        } else {
            final UserDTO user = this.currentSession.getCurrentUser();
            if (user == null) {
                throw new ApiUnauthorizedException("Vous devez être connecté pour liker un commentaire.");
            }

            final PacifistaNewsCommentLikeDTO likeDTO = this.findLikeDtoForComment(user, commentId);

            if (likeDTO == null) {
                final PacifistaNewsCommentLikeDTO newLikeDTO = new PacifistaNewsCommentLikeDTO();
                newLikeDTO.setComment(commentDTO);
                newLikeDTO.setNews(commentDTO.getNews());

                final PacifistaNewsCommentLikeDTO result = this.likeService.create(newLikeDTO);
                this.service.setCommentLikes(commentDTO.getId(), commentDTO.getLikes() + 1);
                return result;
            } else {
                throw new ApiBadRequestException("Vous avez déjà liké ce commentaire.");
            }
        }
    }

    @Override
    @Transactional
    public void removeLike(String commentId) {
        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);
        final boolean canUserEdit = this.newsService.isCurrentUserStaff();
        final UserDTO user = this.currentSession.getCurrentUser();
        if (user == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour liker un commentaire.");
        }

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !canUserEdit) {
            throw new ApiUnauthorizedException("Vous n'avez pas la permission de modifier les brouillons.");
        } else {
            final PacifistaNewsCommentLikeDTO likeDTO = this.findLikeDtoForComment(user, commentId);
            if (likeDTO == null) {
                throw new ApiNotFoundException("Vous n'avez pas liké ce commentaire.");
            }

            this.likeService.delete(likeDTO.getId().toString());
            this.service.setCommentLikes(commentDTO.getId(), commentDTO.getLikes() - 1);
        }
    }

    @Nullable
    private PacifistaNewsCommentLikeDTO findLikeDtoForComment(final UserDTO user, final String commentId) {
        try {
            return this.likeService.getAll(
                    "0",
                    "1",
                    String.format(
                            "comment.uuid:%s:%s,funixProdUserId:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            commentId,
                            SearchOperation.EQUALS.getOperation(),
                            user.getId()
                    ),
                    "createdAt:desc"
            ).getContent().getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private PacifistaNewsCommentDTO checkFilterEditOrCreate(@NonNull String commentId, boolean isEdit) {
        if (Boolean.TRUE.equals(this.banService.isCurrentUserBanned())) {
            throw new ApiForbiddenException("Vous avez été banni de l'espace commentaire et ne pouvez pas commenter.");
        }
        final UserDTO user = this.currentSession.getCurrentUser();
        if (user == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour commenter.");
        }
        final boolean userIsStaff = this.newsService.isCurrentUserStaff();

        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !userIsStaff) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de " + (isEdit ? "modifier" : "supprimer") + " les brouillons.");
        }
        if (!userIsStaff && !user.getId().equals(commentDTO.getFunixProdUserId())) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de " + (isEdit ? "modifier" : "supprimer") + " ce commentaire.");
        }

        return commentDTO;
    }
}
