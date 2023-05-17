package fr.pacifista.api.service.server.guilds.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.server.guilds.dtos.GuildHomeDTO;
import fr.pacifista.api.service.server.guilds.entities.GuildHome;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuildHomeMapper extends ApiMapper<GuildHome, GuildHomeDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guild", ignore = true)
    GuildHome toEntity(GuildHomeDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "guildId", source = "guild.uuid")
    GuildHomeDTO toDto(GuildHome entity);

    @Override
    @Mapping(target = "guild", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildHome request, @MappingTarget GuildHome toPatch);
}
