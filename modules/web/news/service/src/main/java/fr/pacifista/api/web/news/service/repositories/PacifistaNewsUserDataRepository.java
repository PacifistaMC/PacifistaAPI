package fr.pacifista.api.web.news.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PacifistaNewsUserDataRepository<T extends PacifistaNewsUserData> extends ApiRepository<T> {

    List<T> findAllByNews(PacifistaNews news);

}
