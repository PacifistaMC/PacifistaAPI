package fr.pacifista.api.server.warps.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import fr.pacifista.api.server.warps.service.entities.WarpPortal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WarpMapper.class})
public interface WarpPortalMapper extends ApiMapper<WarpPortal, WarpPortalDTO> {
}
