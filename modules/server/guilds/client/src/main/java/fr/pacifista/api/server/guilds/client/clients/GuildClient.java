package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Guilds", url = "${pacifista.api.server.guilds.app-domain-url}", path = "/guilds")
public interface GuildClient extends CrudClient<GuildDTO> {
}
