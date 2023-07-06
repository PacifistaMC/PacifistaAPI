package fr.pacifista.api.server.box.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;
import fr.pacifista.api.server.box.service.entities.BoxReward;
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

}
