package fr.pacifista.api.server.permissions.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
