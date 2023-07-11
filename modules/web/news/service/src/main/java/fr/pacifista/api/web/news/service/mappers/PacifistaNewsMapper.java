package fr.pacifista.api.web.news.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.PacifistaNews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaNewsMapper extends ApiMapper<PacifistaNews, PacifistaNewsDTO> {
}
