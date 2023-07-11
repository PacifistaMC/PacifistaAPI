package fr.pacifista.api.server.guilds.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        GuildExperienceMapper.class,
        GuildHomeMapper.class,
        GuildMemberMapper.class,
})
public interface GuildMapper extends ApiMapper<Guild, GuildDTO> {
}
