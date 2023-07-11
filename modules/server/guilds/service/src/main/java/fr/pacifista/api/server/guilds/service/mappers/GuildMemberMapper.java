package fr.pacifista.api.server.guilds.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.guilds.client.dtos.GuildMemberDTO;
import fr.pacifista.api.server.guilds.service.entities.GuildMember;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuildMemberMapper extends ApiMapper<GuildMember, GuildMemberDTO> {
    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guild", ignore = true)
    GuildMember toEntity(GuildMemberDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "guildId", source = "guild.uuid")
    GuildMemberDTO toDto(GuildMember entity);

    @Override
    @Mapping(target = "guild", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildMember request, @MappingTarget GuildMember toPatch);
}
