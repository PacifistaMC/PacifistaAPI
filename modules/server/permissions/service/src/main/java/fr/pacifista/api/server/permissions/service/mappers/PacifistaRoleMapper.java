package fr.pacifista.api.server.permissions.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaRoleDTO;
import fr.pacifista.api.server.permissions.service.entities.PacifistaRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        PacifistaPermissionMapper.class
})
public interface PacifistaRoleMapper extends ApiMapper<PacifistaRole, PacifistaRoleDTO> {
}
