package fr.pacifista.api.web.user.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebLegal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaWebLegalMapper extends ApiMapper<PacifistaWebLegal, PacifistaWebLegalDTO> {
}
