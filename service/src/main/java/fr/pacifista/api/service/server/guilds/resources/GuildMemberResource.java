package fr.pacifista.api.service.server.guilds.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import fr.pacifista.api.service.server.guilds.services.GuildMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guilds/members")
public class GuildMemberResource extends ApiResource<GuildMemberDTO, GuildMemberService> {
    public GuildMemberResource(GuildMemberService guildMemberService) {
        super(guildMemberService);
    }
}
