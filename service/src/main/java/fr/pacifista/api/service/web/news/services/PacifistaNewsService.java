package fr.pacifista.api.service.web.news.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.web.news.dtos.PacifistaNewsDTO;
import fr.pacifista.api.service.core.auth.entities.Session;
import fr.pacifista.api.service.core.auth.services.ActualSession;
import fr.pacifista.api.service.web.news.entities.PacifistaNews;
import fr.pacifista.api.service.web.news.mappers.PacifistaNewsMapper;
import fr.pacifista.api.service.web.news.repositories.PacifistaNewsRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PacifistaNewsService extends ApiService<PacifistaNewsDTO, PacifistaNews, PacifistaNewsMapper, PacifistaNewsRepository> {

    private final ActualSession actualSession;

    public PacifistaNewsService(PacifistaNewsRepository repository,
                                PacifistaNewsMapper mapper,
                                ActualSession actualSession) {
        super(repository, mapper);
        this.actualSession = actualSession;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNews> entity) {
        final Session session = this.actualSession.getActualSession();

        if (session != null) {
            for (final PacifistaNews e : entity) {
                if (e.getId() == null) {
                    e.setOriginalWriter(session.getUser().getUsername());
                } else {
                    e.setUpdateWriter(session.getUser().getUsername());
                }
            }
        }
    }
}
