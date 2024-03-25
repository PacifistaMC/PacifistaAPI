package fr.pacifista.api.server.essentials.service.homes.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;
import fr.pacifista.api.server.essentials.service.homes.entities.Home;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomeMapper extends ApiMapper<Home, HomeDTO> {
}
