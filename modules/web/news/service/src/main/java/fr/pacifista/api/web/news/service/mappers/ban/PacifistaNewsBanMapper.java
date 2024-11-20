package fr.pacifista.api.web.news.service.mappers.ban;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.service.entities.ban.PacifistaNewsBan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaNewsBanMapper extends ApiMapper<PacifistaNewsBan, PacifistaNewsBanDTO> {
}
