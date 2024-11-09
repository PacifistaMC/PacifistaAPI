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
    @Mapping(target = "articleImageId", ignore = true)
    @Mapping(target = "articleImageIdLowRes", ignore = true)
    PacifistaNewsDTO toDto(PacifistaNews entity);

}
