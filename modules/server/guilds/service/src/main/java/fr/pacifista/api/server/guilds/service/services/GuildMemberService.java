package fr.pacifista.api.server.guilds.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.guilds.client.dtos.GuildMemberDTO;
import fr.pacifista.api.server.guilds.service.entities.Guild;
import fr.pacifista.api.server.guilds.service.entities.GuildMember;
import fr.pacifista.api.server.guilds.service.mappers.GuildMemberMapper;
import fr.pacifista.api.server.guilds.service.repositories.GuildMemberRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GuildMemberService extends ApiService<GuildMemberDTO, GuildMember, GuildMemberMapper, GuildMemberRepository> {

    private final GuildService guildService;

    public GuildMemberService(GuildMemberRepository repository,
                              GuildService guildService,
                              GuildMemberMapper mapper) {
        super(repository, mapper);
        this.guildService = guildService;
    }

    @Override
    public void afterMapperCall(@NonNull GuildMemberDTO dto, @NonNull GuildMember entity) {
        if (dto.getId() == null) {
            final Guild guild = guildService.findGuildById(dto.getGuildId());
            entity.setGuild(guild);
        }
    }

}
