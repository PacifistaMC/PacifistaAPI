package fr.pacifista.api.web.news.service.repositories.news;

import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsLike;
import fr.pacifista.api.web.news.service.repositories.PacifistaNewsUserDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaNewsLikeRepository extends PacifistaNewsUserDataRepository<PacifistaNewsLike> {
}
