package fr.pacifista.api.service.guilds.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.service.guilds.entities.GuildMember;
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
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(GuildMember request, @MappingTarget GuildMember toPatch);
}
