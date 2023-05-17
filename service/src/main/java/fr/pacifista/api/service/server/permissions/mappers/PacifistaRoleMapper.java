package fr.pacifista.api.service.server.permissions.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.server.permissions.entities.PacifistaRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        PacifistaPermissionMapper.class
})
public interface PacifistaRoleMapper extends ApiMapper<PacifistaRole, PacifistaRoleDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PacifistaRole toEntity(PacifistaRoleDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PacifistaRoleDTO toDto(PacifistaRole entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaRole request, @MappingTarget PacifistaRole toPatch);
}
