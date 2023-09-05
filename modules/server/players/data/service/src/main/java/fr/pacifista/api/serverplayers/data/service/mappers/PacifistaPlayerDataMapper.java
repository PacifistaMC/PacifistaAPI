package fr.pacifista.api.serverplayers.data.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.serverplayers.data.service.entities.PacifistaPlayerData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaPlayerDataMapper extends ApiMapper<PacifistaPlayerData, PacifistaPlayerDataDTO> {
}
