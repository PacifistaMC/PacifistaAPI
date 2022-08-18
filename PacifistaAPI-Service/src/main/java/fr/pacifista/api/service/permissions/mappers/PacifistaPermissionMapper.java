package fr.pacifista.api.service.permissions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PacifistaPermissionMapper extends ApiMapper<PacifistaPermission, PacifistaPermissionDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "role.uuid", source = "roleId")
    PacifistaPermission toEntity(PacifistaPermissionDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "roleId", source = "role.uuid")
    PacifistaPermissionDTO toDto(PacifistaPermission entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaPermission request, @MappingTarget PacifistaPermission toPatch);
}
