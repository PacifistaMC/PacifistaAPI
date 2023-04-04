package fr.pacifista.api.service.guilds.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.guilds.dtos.GuildLogDTO;
import fr.pacifista.api.service.guilds.entities.GuildLog;
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
