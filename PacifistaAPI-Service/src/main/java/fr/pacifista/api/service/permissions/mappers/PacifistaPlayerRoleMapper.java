package fr.pacifista.api.service.permissions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PacifistaRoleMapper.class)
public interface PacifistaPlayerRoleMapper extends ApiMapper<PacifistaPlayerRole, PacifistaPlayerRoleDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PacifistaPlayerRole toEntity(PacifistaPlayerRoleDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    PacifistaPlayerRoleDTO toDto(PacifistaPlayerRole entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaPlayerRole request, @MappingTarget PacifistaPlayerRole toPatch);
}
