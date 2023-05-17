package fr.pacifista.api.service.server.permissions.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.server.permissions.entities.PacifistaPermission;
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
