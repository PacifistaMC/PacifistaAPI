package fr.pacifista.api.web.user.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebLegalUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaWebLegalUserMapper extends ApiMapper<PacifistaWebLegalUser, PacifistaWebLegalUserDTO> {
}
