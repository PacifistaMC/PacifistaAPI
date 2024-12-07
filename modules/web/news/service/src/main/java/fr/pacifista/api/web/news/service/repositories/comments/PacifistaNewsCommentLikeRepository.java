package fr.pacifista.api.web.news.service.repositories.comments;

import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsCommentLike;
import fr.pacifista.api.web.news.service.repositories.PacifistaNewsUserDataRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PacifistaNewsCommentLikeRepository extends PacifistaNewsUserDataRepository<PacifistaNewsCommentLike> {
    Collection<PacifistaNewsCommentLike> findAllByCommentInAndMinecraftUsernameIgnoreCaseAndFunixProdUserId(Iterable<PacifistaNewsComment> comments, String minecraftUsername, String funixProdUserId);
    void deleteAllByCommentIn(Iterable<PacifistaNewsComment> comments);
}
