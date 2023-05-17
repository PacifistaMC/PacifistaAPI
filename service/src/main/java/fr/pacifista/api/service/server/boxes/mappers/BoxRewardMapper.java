package fr.pacifista.api.service.server.boxes.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.server.boxes.entities.BoxReward;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BoxRewardMapper extends ApiMapper<BoxReward, BoxRewardDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "box", ignore = true)
    BoxReward toEntity(BoxRewardDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "boxId", source = "box.uuid")
    BoxRewardDTO toDto(BoxReward entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(BoxReward request, @MappingTarget BoxReward toPatch);
}
