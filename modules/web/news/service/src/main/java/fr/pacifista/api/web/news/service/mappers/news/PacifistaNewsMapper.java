package fr.pacifista.api.web.news.service.mappers.news;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacifistaNewsMapper extends ApiMapper<PacifistaNews, PacifistaNewsDTO> {

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "liked", constant = "false")
    PacifistaNewsDTO toDto(PacifistaNews entity);

}
