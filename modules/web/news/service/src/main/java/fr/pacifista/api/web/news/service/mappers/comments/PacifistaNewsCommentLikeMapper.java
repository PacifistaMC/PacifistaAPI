package fr.pacifista.api.web.news.service.mappers.comments;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsCommentLike;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PacifistaNewsCommentMapper.class, PacifistaNewsMapper.class})
public interface PacifistaNewsCommentLikeMapper extends ApiMapper<PacifistaNewsCommentLike, PacifistaNewsCommentLikeDTO> {
}
