package fr.pacifista.api.service.boxes.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.boxes.entities.PlayerBox;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BoxMapper.class)
public interface PlayerBoxMapper extends ApiMapper<PlayerBox, PlayerBoxDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PlayerBox toEntity(PlayerBoxDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PlayerBoxDTO toDto(PlayerBox entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PlayerBox request, @MappingTarget PlayerBox toPatch);
}
