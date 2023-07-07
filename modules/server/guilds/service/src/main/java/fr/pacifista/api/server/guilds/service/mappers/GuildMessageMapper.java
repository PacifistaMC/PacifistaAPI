package fr.pacifista.api.server.guilds.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.guilds.client.dtos.GuildMessageDTO;
import fr.pacifista.api.server.guilds.service.entities.GuildMessage;
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
