package fr.pacifista.api.service.permissions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleHeritageDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PacifistaPermissionMapper.class)
public interface PacifistaRoleHeritageMapper extends ApiMapper<PacifistaRoleHeritage, PacifistaRoleHeritageDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "heritage", ignore = true)
    @Mapping(target = "role.id", ignore = true)
    @Mapping(target = "heritage.id", ignore = true)
    @Mapping(target = "role.uuid", source = "role.id")
    @Mapping(target = "heritage.uuid", source = "heritage.id")
    PacifistaRoleHeritage toEntity(PacifistaRoleHeritageDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "heritage", ignore = true)
    @Mapping(target = "role.id", source = "role.uuid")
    @Mapping(target = "heritage.id", source = "heritage.uuid")
    @Mapping(target = "role.name", source = "role.name")
    @Mapping(target = "heritage.name", source = "heritage.name")
    @Mapping(target = "role.permissions", source = "role.permissions")
    @Mapping(target = "heritage.permissions", source = "heritage.permissions")
    PacifistaRoleHeritageDTO toDto(PacifistaRoleHeritage entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaRoleHeritage request, @MappingTarget PacifistaRoleHeritage toPatch);
}
