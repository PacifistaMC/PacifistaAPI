package fr.pacifista.api.server.warps.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WarpMapper.class})
public interface WarpPlayerInteractionMapper extends ApiMapper<WarpPlayerInteraction, WarpPlayerInteractionDTO> {
}
