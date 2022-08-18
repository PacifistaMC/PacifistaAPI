package fr.pacifista.api.service.permissions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleHeritageDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PacifistaRoleHeritageMapper extends ApiMapper<PacifistaRoleHeritage, PacifistaRoleHeritageDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PacifistaRoleHeritage toEntity(PacifistaRoleHeritageDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PacifistaRoleHeritageDTO toDto(PacifistaRoleHeritage entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaRoleHeritage request, @MappingTarget PacifistaRoleHeritage toPatch);
}
