package fr.pacifista.api.service.server.players.players_sync.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerInventoryDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerInventoryData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlayerInventoryDataMapper extends ApiMapper<PlayerInventoryData, PlayerInventoryDataDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PlayerInventoryData toEntity(PlayerInventoryDataDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PlayerInventoryDataDTO toDto(PlayerInventoryData entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PlayerInventoryData request, @MappingTarget PlayerInventoryData toPatch);
}
