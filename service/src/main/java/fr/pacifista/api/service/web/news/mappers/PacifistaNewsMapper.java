package fr.pacifista.api.service.web.news.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.web.news.dtos.PacifistaNewsDTO;
import fr.pacifista.api.service.web.news.entities.PacifistaNews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaNewsMapper extends ApiMapper<PacifistaNews, PacifistaNewsDTO> {
}
