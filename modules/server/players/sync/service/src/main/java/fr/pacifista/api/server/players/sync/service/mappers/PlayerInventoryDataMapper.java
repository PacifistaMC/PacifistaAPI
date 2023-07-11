package fr.pacifista.api.server.players.sync.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerInventoryData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerInventoryDataMapper extends ApiMapper<PlayerInventoryData, PlayerInventoryDataDTO> {
}
