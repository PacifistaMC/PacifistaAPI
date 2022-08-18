package fr.pacifista.api.service.permissions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        PacifistaRoleHeritageMapper.class,
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
