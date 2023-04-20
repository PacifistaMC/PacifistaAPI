package fr.pacifista.api.service.server.players.players_sync.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.service.server.players.players_sync.entities.PlayerExperienceData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlayerExperienceDataMapper extends ApiMapper<PlayerExperienceData, PlayerExperienceDataDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PlayerExperienceData toEntity(PlayerExperienceDataDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PlayerExperienceDataDTO toDto(PlayerExperienceData entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PlayerExperienceData request, @MappingTarget PlayerExperienceData toPatch);
}
