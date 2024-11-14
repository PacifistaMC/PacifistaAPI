package fr.pacifista.api.web.news.service.services.ban;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.service.entities.ban.PacifistaNewsBan;
import fr.pacifista.api.web.news.service.mappers.ban.PacifistaNewsBanMapper;
import fr.pacifista.api.web.news.service.repositories.ban.PacifistaNewsBanRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaNewsBanCrudService extends ApiService<PacifistaNewsBanDTO, PacifistaNewsBan, PacifistaNewsBanMapper, PacifistaNewsBanRepository> {

    public PacifistaNewsBanCrudService(PacifistaNewsBanMapper mapper, PacifistaNewsBanRepository repository) {
        super(repository, mapper);
    }

}
