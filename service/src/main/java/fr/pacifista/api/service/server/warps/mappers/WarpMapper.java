package fr.pacifista.api.service.server.warps.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.warps.dtos.WarpDTO;
import fr.pacifista.api.service.server.warps.entities.Warp;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface WarpMapper extends ApiMapper<Warp, WarpDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    Warp toEntity(WarpDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    WarpDTO toDto(Warp entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(Warp request, @MappingTarget Warp toPatch);
}
