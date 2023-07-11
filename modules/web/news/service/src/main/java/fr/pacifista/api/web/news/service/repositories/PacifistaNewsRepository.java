package fr.pacifista.api.web.news.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.news.service.entities.PacifistaNews;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaNewsRepository extends ApiRepository<PacifistaNews> {
}
