package fr.pacifista.api.server.box.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
import fr.pacifista.api.server.box.service.entities.PlayerBox;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BoxMapper.class)
public interface PlayerBoxMapper extends ApiMapper<PlayerBox, PlayerBoxDTO> {
}
