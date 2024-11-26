package fr.pacifista.api.web.news.service.mappers.comments;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PacifistaNewsMapper.class})
public interface PacifistaNewsCommentMapper extends ApiMapper<PacifistaNewsComment, PacifistaNewsCommentDTO> {

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "liked", ignore = true)
    PacifistaNewsCommentDTO toDto(PacifistaNewsComment entity);

}
