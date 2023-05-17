package fr.pacifista.api.service.server.boxes.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.server.boxes.entities.Box;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BoxRewardMapper.class)
public interface BoxMapper extends ApiMapper<Box, BoxDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    Box toEntity(BoxDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    BoxDTO toDto(Box entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "rewards", ignore = true)
    void patch(Box request, @MappingTarget Box toPatch);
}
