package fr.pacifista.api.service.server.guilds.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.service.server.guilds.entities.Guild;
import fr.pacifista.api.service.server.guilds.entities.GuildMember;
import fr.pacifista.api.service.server.guilds.mappers.GuildMemberMapper;
import fr.pacifista.api.service.server.guilds.repositories.GuildMemberRepository;
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
