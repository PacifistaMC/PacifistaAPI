package fr.pacifista.api.service.server.guilds.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.guilds.dtos.GuildMessageDTO;
import fr.pacifista.api.service.server.guilds.entities.GuildMessage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuildMessageMapper extends ApiMapper<GuildMessage, GuildMessageDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guild", ignore = true)
    GuildMessage toEntity(GuildMessageDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "guildId", source = "guild.uuid")
    GuildMessageDTO toDto(GuildMessage entity);

    @Override
    @Mapping(target = "guild", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildMessage request, @MappingTarget GuildMessage toPatch);
}
