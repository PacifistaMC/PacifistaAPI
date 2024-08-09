package fr.pacifista.api.server.essentials.service.holograms.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.holograms.dtos.HologramDTO;
import fr.pacifista.api.server.essentials.service.holograms.entities.Hologram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HologramMapper extends ApiMapper<Hologram, HologramDTO> {
    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "parentHologram.childHolograms", ignore = true)
    @Mapping(target = "parentHologram.parentHologram", ignore = true)
    @Mapping(target = "parentHologram.id", source = "parentHologram.uuid")
    HologramDTO toDto(Hologram entity);
}
