package fr.pacifista.api.web.user.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebUserLink;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PacifistaWebUserLinkMapper extends ApiMapper<PacifistaWebUserLink, PacifistaWebUserLinkDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    PacifistaWebUserLink toEntity(PacifistaWebUserLinkDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "minecraftUsername", ignore = true)
    PacifistaWebUserLinkDTO toDto(PacifistaWebUserLink entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PacifistaWebUserLink request, @MappingTarget PacifistaWebUserLink toPatch);
}
