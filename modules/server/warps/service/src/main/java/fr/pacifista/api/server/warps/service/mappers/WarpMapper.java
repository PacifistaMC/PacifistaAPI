package fr.pacifista.api.server.warps.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WarpConfigMapper.class})
public interface WarpMapper extends ApiMapper<Warp, WarpDTO> {
}
