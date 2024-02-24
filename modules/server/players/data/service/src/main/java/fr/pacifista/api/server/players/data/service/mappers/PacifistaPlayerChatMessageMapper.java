package fr.pacifista.api.server.players.data.service.mappers;


import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerChatMessageDTO;
import fr.pacifista.api.server.players.data.service.entities.PacifistaPlayerChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaPlayerChatMessageMapper extends ApiMapper<PacifistaPlayerChatMessage, PacifistaPlayerChatMessageDTO> {
}
