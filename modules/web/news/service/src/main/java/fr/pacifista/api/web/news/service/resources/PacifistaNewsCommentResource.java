package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsCommentClient;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.services.ban.PacifistaNewsBanCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentLikeCrudService;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsCrudService;
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
                            "news.uuid:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            newsId
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
                    "20",
                    String.format(
                            "parent.uuid:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            commentId
                    ),
                    "likes:desc"
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
                "likes:desc"
        );
    }

    @Override
    public PacifistaNewsCommentDTO createComment(final PacifistaNewsCommentDTO commentDTO) {
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

            return this.service.create(commentDTO);
        }
    }

    @Override
    public PacifistaNewsCommentDTO updateComment(String commentId, String comment) {
        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);
        final boolean canUserEdit = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !canUserEdit) {
            throw new ApiForbiddenException("Vous n'avez pas la permission de modifier les brouillons.");
        } else {
            commentDTO.setContent(comment);

            return this.service.updatePut(commentDTO);
        }
    }

    @Override
    public void deleteComment(String commentId) {

    }

    @Override
    public PageDTO<PacifistaNewsCommentLikeDTO> getLikesOnComment(String commentId, int page) {

    }

    @Override
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

                commentDTO.setLikes(commentDTO.getLikes() + 1);
                this.service.updatePut(commentDTO);

                return result;
            } else {
                throw new ApiBadRequestException("Vous avez déjà liké ce commentaire.");
            }
        }
    }

    @Override
    public void removeLike(String commentId) {
        final PacifistaNewsCommentDTO commentDTO = this.service.findById(commentId);
        final boolean canUserEdit = this.newsService.isCurrentUserStaff();

        if (Boolean.TRUE.equals(commentDTO.getNews().getDraft()) && !canUserEdit) {
            throw new ApiUnauthorizedException("Vous n'avez pas la permission de modifier les brouillons.");
        } else {
            this.likeService.delete(commentId);
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
}
