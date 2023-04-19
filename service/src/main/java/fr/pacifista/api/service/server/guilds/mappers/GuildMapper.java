package fr.pacifista.api.service.server.guilds.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.service.server.guilds.entities.Guild;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        GuildExperienceMapper.class,
        GuildHomeMapper.class,
        GuildMemberMapper.class,
})
public interface GuildMapper extends ApiMapper<Guild, GuildDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    Guild toEntity(GuildDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    GuildDTO toDto(Guild entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(Guild request, @MappingTarget Guild toPatch);
}
