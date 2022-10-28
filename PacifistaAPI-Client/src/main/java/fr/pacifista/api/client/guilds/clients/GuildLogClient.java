package fr.pacifista.api.client.guilds.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.guilds.dtos.GuildLogDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsLog", url = "${pacifista.api.app-domain-url}", path = "/guilds/logs")
public interface GuildLogClient extends CrudClient<GuildLogDTO> {
}
