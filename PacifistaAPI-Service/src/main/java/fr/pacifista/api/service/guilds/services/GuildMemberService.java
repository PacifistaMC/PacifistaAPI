package fr.pacifista.api.service.guilds.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.service.guilds.entities.Guild;
import fr.pacifista.api.service.guilds.entities.GuildMember;
import fr.pacifista.api.service.guilds.mappers.GuildMemberMapper;
import fr.pacifista.api.service.guilds.repositories.GuildMemberRepository;
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
    public void beforeSavingEntity(@NonNull GuildMemberDTO request, @NonNull GuildMember entity) {
        if (request.getId() != null) {
            final Guild guild = guildService.findGuildById(request.getGuildId());
            entity.setGuild(guild);
        }
    }
}
