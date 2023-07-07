package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.guilds.client.dtos.GuildLogDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsLog", url = "${pacifista.api.server.guilds.app-domain-url}", path = "/guilds/logs")
public interface GuildLogClient extends CrudClient<GuildLogDTO> {
}
