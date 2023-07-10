package fr.pacifista.api.server.permissions.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.server.permissions.service.entities.PacifistaPlayerRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PacifistaRoleMapper.class)
public interface PacifistaPlayerRoleMapper extends ApiMapper<PacifistaPlayerRole, PacifistaPlayerRoleDTO> {
}
