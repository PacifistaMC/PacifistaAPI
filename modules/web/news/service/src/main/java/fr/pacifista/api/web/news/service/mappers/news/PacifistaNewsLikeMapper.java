package fr.pacifista.api.web.news.service.mappers.news;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PacifistaNewsMapper.class)
public interface PacifistaNewsLikeMapper extends ApiMapper<PacifistaNewsLike, PacifistaNewsLikeDTO> {
}
