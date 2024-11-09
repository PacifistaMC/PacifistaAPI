package fr.pacifista.api.web.news.service.repositories.ban;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.news.service.entities.ban.PacifistaNewsBan;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaNewsBanRepository extends ApiRepository<PacifistaNewsBan> {
}
