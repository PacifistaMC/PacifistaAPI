package fr.pacifista.api.server.guilds.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.guilds.client.dtos.GuildExperienceDTO;
import fr.pacifista.api.server.guilds.service.entities.GuildExperience;
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
    @Mapping(target = "guild", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildExperience request, @MappingTarget GuildExperience toPatch);
}
