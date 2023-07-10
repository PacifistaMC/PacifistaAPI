package fr.pacifista.api.server.players.sync.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerMoneyData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMoneyDataMapper extends ApiMapper<PlayerMoneyData, PlayerMoneyDataDTO> {
}
