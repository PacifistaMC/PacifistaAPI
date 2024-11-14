package fr.pacifista.api.web.news.service.repositories.news;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacifistaNewsImageRepository extends ApiRepository<PacifistaNewsImage> {
    List<PacifistaNewsImage> findAllByNewsUuid(String newsUuid);
}
