package fr.pacifista.api.service.players.players_sync.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerMoneyDataDTO;
import fr.pacifista.api.service.players.players_sync.entities.PlayerMoneyData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlayerMoneyDataMapper extends ApiMapper<PlayerMoneyData, PlayerMoneyDataDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PlayerMoneyData toEntity(PlayerMoneyDataDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PlayerMoneyDataDTO toDto(PlayerMoneyData entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PlayerMoneyData request, @MappingTarget PlayerMoneyData toPatch);
}
