package fr.pacifista.api.server.players.sync.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerEnderchestData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerEnderchestDataMapper extends ApiMapper<PlayerEnderchestData, PlayerEnderchestDataDTO> {
}
