package fr.pacifista.api.web.news.service.repositories.comments;

import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.repositories.PacifistaNewsUserDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaNewsCommentRepository extends PacifistaNewsUserDataRepository<PacifistaNewsComment> {
}
