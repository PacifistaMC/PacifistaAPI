package fr.pacifista.api.web.news.service.repositories.news;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacifistaNewsRepository extends ApiRepository<PacifistaNews> {

    Optional<PacifistaNews> findByNameIgnoreCase(String name);

}
