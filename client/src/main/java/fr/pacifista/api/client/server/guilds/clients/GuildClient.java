package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Guilds", url = "${pacifista.api.app-domain-url}", path = "/guilds")
public interface GuildClient extends CrudClient<GuildDTO> {
}
