package fr.pacifista.api.web.news.service.services;

import com.funixproductions.api.user.client.dtos.UserSession;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PacifistaNewsService extends ApiService<PacifistaNewsDTO, PacifistaNews, PacifistaNewsMapper, PacifistaNewsRepository> {

    private final CurrentSession actualSession;

    public PacifistaNewsService(PacifistaNewsRepository repository,
                                PacifistaNewsMapper mapper,
                                CurrentSession actualSession) {
        super(repository, mapper);
        this.actualSession = actualSession;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNews> entity) {
        final UserSession session = this.actualSession.getUserSession();

        if (session != null) {
            for (final PacifistaNews e : entity) {
                if (e.getId() == null) {
                    e.setOriginalWriter(session.getUserDTO().getUsername());
                } else {
                    e.setUpdateWriter(session.getUserDTO().getUsername());
                }
            }
        }
    }
}
