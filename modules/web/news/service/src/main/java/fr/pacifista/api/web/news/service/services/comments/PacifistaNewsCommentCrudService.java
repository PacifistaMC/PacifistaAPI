package fr.pacifista.api.web.news.service.services.comments;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.core.service.tools.discord.dtos.DiscordSendMessageWebHookDTO;
import fr.pacifista.api.core.service.tools.discord.services.DiscordMessagesService;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsCommentLike;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.mappers.comments.PacifistaNewsCommentMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentLikeRepository;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.news.service.services.PacifistaNewsUserService;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PacifistaNewsCommentCrudService extends PacifistaNewsUserService<PacifistaNewsCommentDTO, PacifistaNewsComment, PacifistaNewsCommentMapper, PacifistaNewsCommentRepository> {

    private final PacifistaNewsRepository newsRepository;
    private final PacifistaNewsCommentLikeRepository commentLikeRepository;

    private final CurrentSession currentSession;
    private final PacifistaWebUserLinkComponent webUserLinkComponent;
    private final DiscordMessagesService discordMessagesService;

    public PacifistaNewsCommentCrudService(PacifistaNewsCommentRepository repository,
                                           PacifistaNewsCommentMapper mapper,
                                           PacifistaNewsRepository newsRepository,
                                           CurrentSession currentSession,
                                           PacifistaWebUserLinkComponent webUserLinkComponent,
                                           PacifistaNewsCommentLikeRepository commentLikeRepository,
                                           DiscordMessagesService discordMessagesService) {
        super(repository, mapper);
        this.newsRepository = newsRepository;
        this.currentSession = currentSession;
        this.webUserLinkComponent = webUserLinkComponent;
        this.commentLikeRepository = commentLikeRepository;
        this.discordMessagesService = discordMessagesService;
    }

    @Override
    public void beforeSendingDTO(@NonNull Iterable<PacifistaNewsCommentDTO> dtos) {
        final UserDTO currentUser = this.currentSession.getCurrentUser();
        if (currentUser == null) return;

        try {
            final PacifistaWebUserLinkDTO link = this.webUserLinkComponent.getLink(currentUser);
            final Set<String> commentIdsLIst = new HashSet<>();

            for (PacifistaNewsCommentDTO commentDTO : dtos) {
                commentIdsLIst.add(commentDTO.getId().toString());
            }
            final Iterable<PacifistaNewsComment> allComments = this.getRepository().findAllByUuidIn(commentIdsLIst);
            final Iterable<PacifistaNewsCommentLike> allLikes = this.commentLikeRepository.findAllByCommentInAndMinecraftUsernameIgnoreCaseAndFunixProdUserId(allComments, link.getMinecraftUsername(), link.getFunixProdUserId());

            for (PacifistaNewsCommentDTO commentDTO : dtos) {
                commentDTO.setLiked(
                        this.checkIfLikeInCommentListFound(commentDTO, allLikes)
                );
            }
        } catch (ApiBadRequestException ignored) {
        }
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsComment> pacifistaNewsComments) {
        super.beforeSavingEntity(pacifistaNewsComments);

        PacifistaNewsComment parent;
        for (PacifistaNewsComment pacifistaNewsComment : pacifistaNewsComments) {
            parent = pacifistaNewsComment.getParent();

            if (parent != null && parent.getUuid() != null) {
                parent = this.getRepository().findByUuid(parent.getUuid().toString()).orElseThrow(() -> new ApiNotFoundException("Le commentaire parent n'existe pas"));
                if (parent.getParent() != null) {
                    throw new ApiBadRequestException("Les commentaires imbriqués ne sont pas autorisés (parent -> enfant -> petit-enfant)");
                }

                pacifistaNewsComment.setParent(parent);
            }
        }
    }

    @Override
    public void afterSavingEntity(@NonNull Iterable<PacifistaNewsComment> entity) {
        for (PacifistaNewsComment comment : entity) {
            if (comment.getUpdatedAt() == null) {
                this.alertDiscordWhenCommentAdded(comment);
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaNewsComment> entities) {
        final Set<PacifistaNewsComment> comments = new HashSet<>();
        final Map<PacifistaNews, Integer> removeCommentsFromNews = new HashMap<>();
        final Map<PacifistaNewsComment, Integer> removeRepliesFromComments = new HashMap<>();
        PacifistaNews news;
        PacifistaNewsComment parentComment;

        for (final PacifistaNewsComment pacifistaNewsComment : entities) {
            news = pacifistaNewsComment.getNews();
            parentComment = pacifistaNewsComment.getParent();

            comments.add(pacifistaNewsComment);

            if (news != null) {
                removeCommentsFromNews.put(news, removeCommentsFromNews.getOrDefault(news, 0) + 1);
            }
            if (parentComment != null) {
                removeRepliesFromComments.put(parentComment, removeRepliesFromComments.getOrDefault(parentComment, 0) + 1);
            }
        }

        final Iterable<PacifistaNewsComment> childs = this.getRepository().getAllByParentIsIn(comments);
        for (final PacifistaNewsComment child : childs) {
            news = child.getNews();

            if (news != null) {
                removeCommentsFromNews.put(news, removeCommentsFromNews.getOrDefault(news, 0) + 1);
            }
        }
        this.commentLikeRepository.deleteAllByCommentIn(childs);
        super.getRepository().deleteAll(childs);

        final Set<PacifistaNews> newsToSave = new HashSet<>();
        for (final Map.Entry<PacifistaNews, Integer> entry : removeCommentsFromNews.entrySet()) {
            news = entry.getKey();

            news.setComments(news.getComments() - entry.getValue());
            if (news.getComments() < 0) {
                news.setComments(0);
            }

            newsToSave.add(news);
        }
        this.newsRepository.saveAll(newsToSave);

        final Set<PacifistaNewsComment> commentsToSave = new HashSet<>();
        for (final Map.Entry<PacifistaNewsComment, Integer> entry : removeRepliesFromComments.entrySet()) {
            parentComment = entry.getKey();

            parentComment.setReplies(parentComment.getReplies() - entry.getValue());
            if (parentComment.getReplies() < 0) {
                parentComment.setReplies(0);
            }

            commentsToSave.add(parentComment);
        }
        this.getRepository().saveAll(commentsToSave);

        for (final PacifistaNewsComment comment : entities) {
            this.alertDiscordWhenCommentDeleted(comment);
        }

        this.commentLikeRepository.deleteAllByCommentIn(entities);
    }

    public void setCommentLikes(final UUID commentId, final int likes) {
        final Optional<PacifistaNewsComment> search = this.getRepository().findByUuid(commentId.toString());

        if (search.isPresent()) {
            final PacifistaNewsComment comment = search.get();

            comment.setLikes(likes);
            if (likes < 0) {
                comment.setLikes(0);
            }

            this.getRepository().save(comment);
        }
    }

    public void setCommentReplies(final UUID commentId, final int replies) {
        final Optional<PacifistaNewsComment> search = this.getRepository().findByUuid(commentId.toString());

        if (search.isPresent()) {
            final PacifistaNewsComment comment = search.get();

            comment.setReplies(replies);
            if (replies < 0) {
                comment.setReplies(0);
            }

            this.getRepository().save(comment);
        }
    }

    private boolean checkIfLikeInCommentListFound(final PacifistaNewsCommentDTO commentDTO, final Iterable<PacifistaNewsCommentLike> likes) {
        for (final PacifistaNewsCommentLike like : likes) {
            if (like.getComment().getUuid().equals(commentDTO.getId())) {
                return true;
            }
        }

        return false;
    }

    private void alertDiscordWhenCommentAdded(final PacifistaNewsComment comment) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":warning: Nouveau commentaire de %s sur l'article %s : %s.\nLien de l'article : %s",
                comment.getMinecraftUsername(),
                comment.getNews().getTitle(),
                comment.getContent(),
                String.format("<https://pacifista.fr/news/%s>", comment.getNews().getName())
        ));
        message.setUsername("Pacifista News");

        this.discordMessagesService.sendAlertMessage(message);
    }

    public void alertDiscordWhenCommentUpdated(final PacifistaNewsCommentDTO comment) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":warning: Commentaire mis à jour par %s sur l'article %s : **%s**.\nLien de l'article : %s",
                comment.getMinecraftUsername(),
                comment.getNews().getTitle(),
                comment.getContent(),
                String.format("<https://pacifista.fr/news/%s>", comment.getNews().getName())
        ));
        message.setUsername("Pacifista News");

        this.discordMessagesService.sendAlertMessage(message);
    }

    private void alertDiscordWhenCommentDeleted(final PacifistaNewsComment comment) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":warning: Commentaire supprimé de %s sur l'article %s : **%s**.\nLien de l'article : %s",
                comment.getMinecraftUsername(),
                comment.getNews().getTitle(),
                comment.getContent(),
                String.format("<https://pacifista.fr/news/%s>", comment.getNews().getName())
        ));
        message.setUsername("Pacifista News");

        this.discordMessagesService.sendAlertMessage(message);
    }
}
