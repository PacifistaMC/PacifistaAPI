package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.guilds.client.dtos.GuildMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildMember", url = "${pacifista.api.server.guilds.app-domain-url}", path = "/guilds/members")
public interface GuildMemberClient extends CrudClient<GuildMemberDTO> {
}
