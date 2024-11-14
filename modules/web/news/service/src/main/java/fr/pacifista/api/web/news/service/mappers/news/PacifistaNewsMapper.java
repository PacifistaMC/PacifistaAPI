package fr.pacifista.api.web.news.service.mappers.news;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaNewsMapper extends ApiMapper<PacifistaNews, PacifistaNewsDTO> {
}
