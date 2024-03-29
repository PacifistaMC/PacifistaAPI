package fr.pacifista.api.server.guilds.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.guilds.client.dtos.GuildLogDTO;
import fr.pacifista.api.server.guilds.service.entities.GuildLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuildLogMapper extends ApiMapper<GuildLog, GuildLogDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guild", ignore = true)
    GuildLog toEntity(GuildLogDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "guildId", source = "guild.uuid")
    GuildLogDTO toDto(GuildLog entity);

    @Override
    @Mapping(target = "guild", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildLog request, @MappingTarget GuildLog toPatch);
}
