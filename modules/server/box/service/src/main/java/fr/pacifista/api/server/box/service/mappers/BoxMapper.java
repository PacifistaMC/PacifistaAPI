package fr.pacifista.api.server.box.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.service.entities.Box;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BoxRewardMapper.class)
public interface BoxMapper extends ApiMapper<Box, BoxDTO> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "rewards", ignore = true)
    void patch(Box request, @MappingTarget Box toPatch);

}
