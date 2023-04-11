package fr.pacifista.api.service.web.news.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.web.news.entities.PacifistaNews;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaNewsRepository extends ApiRepository<PacifistaNews> {
}