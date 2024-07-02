package fr.pacifista.api.server.essentials.service.ignore_players.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;
import fr.pacifista.api.server.essentials.service.ignore_players.entities.PlayerIgnore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerIgnoreMapper extends ApiMapper<PlayerIgnore, PlayerIgnoreDTO> {
}
