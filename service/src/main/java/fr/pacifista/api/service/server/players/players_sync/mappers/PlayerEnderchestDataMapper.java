package fr.pacifista.api.service.server.players.players_sync.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerEnderchestDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerEnderchestData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlayerEnderchestDataMapper extends ApiMapper<PlayerEnderchestData, PlayerEnderchestDataDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PlayerEnderchestData toEntity(PlayerEnderchestDataDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PlayerEnderchestDataDTO toDto(PlayerEnderchestData entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PlayerEnderchestData request, @MappingTarget PlayerEnderchestData toPatch);

}
