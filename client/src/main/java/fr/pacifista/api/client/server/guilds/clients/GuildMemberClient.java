package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildMember", url = "${pacifista.api.app-domain-url}", path = "/guilds/members")
public interface GuildMemberClient extends CrudClient<GuildMemberDTO> {
}
