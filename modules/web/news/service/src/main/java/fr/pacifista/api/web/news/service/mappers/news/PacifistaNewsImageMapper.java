package fr.pacifista.api.web.news.service.mappers.news;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsImageDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PacifistaNewsMapper.class)
public interface PacifistaNewsImageMapper extends ApiMapper<PacifistaNewsImage, PacifistaNewsImageDTO> {
}
