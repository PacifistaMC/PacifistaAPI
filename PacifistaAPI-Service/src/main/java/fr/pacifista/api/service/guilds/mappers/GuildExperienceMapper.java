package fr.pacifista.api.service.guilds.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.guilds.dtos.GuildExperienceDTO;
import fr.pacifista.api.service.guilds.entities.GuildExperience;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuildExperienceMapper extends ApiMapper<GuildExperience, GuildExperienceDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guild", ignore = true)
    GuildExperience toEntity(GuildExperienceDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "guildId", source = "guild.uuid")
    GuildExperienceDTO toDto(GuildExperience entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildExperience request, @MappingTarget GuildExperience toPatch);
}
