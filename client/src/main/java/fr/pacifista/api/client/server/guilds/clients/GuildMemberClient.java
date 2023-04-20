package fr.pacifista.api.client.guilds.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.guilds.dtos.GuildMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildMember", url = "${pacifista.api.app-domain-url}", path = "/guilds/members")
public interface GuildMemberClient extends CrudClient<GuildMemberDTO> {
}
