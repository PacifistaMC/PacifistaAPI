package fr.pacifista.api.server.players.sync.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import fr.pacifista.api.server.players.sync.service.entities.PlayerExperienceData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerExperienceDataMapper extends ApiMapper<PlayerExperienceData, PlayerExperienceDataDTO> {
}
