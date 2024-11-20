package fr.pacifista.api.web.news.service.services.news;

import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsLike;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsLikeMapper;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsLikeRepository;
import fr.pacifista.api.web.news.service.services.PacifistaNewsUserService;
import org.springframework.stereotype.Service;

@Service
public class PacifistaNewsLikeCrudService extends PacifistaNewsUserService<PacifistaNewsLikeDTO, PacifistaNewsLike, PacifistaNewsLikeMapper, PacifistaNewsLikeRepository> {

    public PacifistaNewsLikeCrudService(PacifistaNewsLikeRepository repository,
                                        PacifistaNewsLikeMapper mapper) {
        super(repository, mapper);
    }

}
