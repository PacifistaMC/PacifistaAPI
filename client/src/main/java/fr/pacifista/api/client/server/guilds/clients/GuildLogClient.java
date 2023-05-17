package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildLogDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsLog", url = "${pacifista.api.app-domain-url}", path = "/guilds/logs")
public interface GuildLogClient extends CrudClient<GuildLogDTO> {
}
