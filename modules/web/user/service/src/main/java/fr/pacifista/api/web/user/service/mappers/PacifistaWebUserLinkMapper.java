package fr.pacifista.api.web.user.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebUserLink;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaWebUserLinkMapper extends ApiMapper<PacifistaWebUserLink, PacifistaWebUserLinkDTO> {
}
