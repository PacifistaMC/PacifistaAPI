package fr.pacifista.api.server.players.data.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerSessionDTO;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaPlayerSessionMapper extends ApiMapper<PacifistaPlayerSession, PacifistaPlayerSessionDTO> {
}
