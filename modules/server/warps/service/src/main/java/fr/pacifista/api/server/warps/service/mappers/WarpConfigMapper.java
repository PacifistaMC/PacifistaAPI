package fr.pacifista.api.server.warps.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WarpMapper.class})
public interface WarpConfigMapper extends ApiMapper<WarpConfig, WarpConfigDTO> {
}
