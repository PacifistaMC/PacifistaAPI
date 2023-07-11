package fr.pacifista.api.server.sanctions.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.service.entities.Sanction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SanctionMapper extends ApiMapper<Sanction, SanctionDTO> {
}
