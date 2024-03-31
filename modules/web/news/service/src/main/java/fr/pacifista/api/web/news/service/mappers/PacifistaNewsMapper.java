package fr.pacifista.api.web.news.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.entities.PacifistaNews;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PacifistaNewsMapper extends ApiMapper<PacifistaNews, PacifistaNewsDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PacifistaNews toEntity(PacifistaNewsDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "likesAmount", ignore = true)
    @Mapping(target = "commentsAmount", ignore = true)
    PacifistaNewsDTO toDto(PacifistaNews entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaNews request, @MappingTarget PacifistaNews toPatch);
}
