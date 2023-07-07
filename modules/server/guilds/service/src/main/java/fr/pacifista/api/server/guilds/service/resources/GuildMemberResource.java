package fr.pacifista.api.server.guilds.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.guilds.client.dtos.GuildMemberDTO;
import fr.pacifista.api.server.guilds.service.services.GuildMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/members")
public class GuildMemberResource extends ApiResource<GuildMemberDTO, GuildMemberService> {
    public GuildMemberResource(GuildMemberService guildMemberService) {
        super(guildMemberService);
    }
}
