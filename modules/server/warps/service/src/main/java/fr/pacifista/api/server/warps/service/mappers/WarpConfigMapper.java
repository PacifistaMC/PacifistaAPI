package fr.pacifista.api.server.warps.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WarpConfigMapper extends ApiMapper<WarpConfig, WarpConfigDTO> {

    @Override
    @Mapping(target = "warp", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "warp.uuid", source = "warp.id")
    @Mapping(target = "warp.id", ignore = true)
    WarpConfig toEntity(WarpConfigDTO dto);

    @Override
    @Mapping(target = "warp", ignore = true)
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "warp.id", source = "warp.uuid")
    WarpConfigDTO toDto(WarpConfig entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(WarpConfig request, @MappingTarget WarpConfig toPatch);
}
